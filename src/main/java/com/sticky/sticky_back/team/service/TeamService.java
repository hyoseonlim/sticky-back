package com.sticky.sticky_back.team.service;

import com.sticky.sticky_back.team.datamodel.TeamEntity;
import com.sticky.sticky_back.team.datamodel.TeamMemberEntity;
import com.sticky.sticky_back.team.datamodel.TeamStatus;
import com.sticky.sticky_back.team.dto.*;
import com.sticky.sticky_back.team.repository.TeamMemberRepository;
import com.sticky.sticky_back.team.repository.TeamRepository;
import com.sticky.sticky_back.user.datamodel.MemberEntity;
import com.sticky.sticky_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    /**
     * 스터디 팀 생성
     */
    @Transactional
    public TeamCreateResponse createTeam(TeamCreateRequest request, Integer creatorId) {
        // 팀 이름 중복 확인
        teamRepository.findByName(request.getName()).ifPresent(team -> {
            throw new RuntimeException("Team name already exists");
        });

        // 팀 엔티티 생성
        TeamEntity team = TeamEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .minimum(request.getMinimum())
                .maximum(request.getMaximum())
                .isPrivate(request.getIsPrivate() != null ? request.getIsPrivate() : false)
                .isOnline(request.getIsOnline() != null ? request.getIsOnline() : true)
                .location(request.getOfflineLocation())
                .status(TeamStatus.RECRUITING)
                .studyCount(0)
                .build();

        TeamEntity savedTeam = teamRepository.save(team);

        // 개설자를 팀원으로 추가
        MemberEntity creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        TeamMemberEntity.TeamMemberId memberId = new TeamMemberEntity.TeamMemberId(
                savedTeam.getTeamId(),
                creatorId
        );

        TeamMemberEntity teamMember = TeamMemberEntity.builder()
                .id(memberId)
                .team(savedTeam)
                .member(creator)
                .build();

        teamMemberRepository.save(teamMember);

        return TeamCreateResponse.builder()
                .teamId(savedTeam.getTeamId())
                .name(savedTeam.getName())
                .creatorId(creatorId)
                .status(savedTeam.getStatus().name())
                .currentMembers(1)
                .build();
    }

    /**
     * 스터디 팀 목록 조회 (필터링 및 페이징)
     */
    public TeamListResponse getTeams(Integer subjectId, String day, String time,
                                     Boolean isOnline, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 모집 중인 팀만 조회
        Page<TeamEntity> teams;
        if (isOnline != null) {
            teams = teamRepository.findByStatusAndIsOnline(TeamStatus.RECRUITING, isOnline, pageable);
        } else {
            teams = teamRepository.findByStatus(TeamStatus.RECRUITING, pageable);
        }

        List<TeamListResponse.TeamSummary> teamSummaries = teams.getContent().stream()
                .map(team -> {
                    long memberCount = teamMemberRepository.countByTeamId(team.getTeamId());
                    return TeamListResponse.TeamSummary.builder()
                            .teamId(team.getTeamId())
                            .name(team.getName())
                            .subject("Subject") // TODO: subject 테이블과 연결 필요
                            .currentMembers((int) memberCount)
                            .maximum(team.getMaximum())
                            .isRecruiting(team.getStatus() == TeamStatus.RECRUITING)
                            .build();
                })
                .collect(Collectors.toList());

        return TeamListResponse.builder()
                .teams(teamSummaries)
                .build();
    }

    /**
     * 스터디 팀 상세 조회
     */
    public TeamDetailResponse getTeamDetail(Integer teamId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        List<TeamMemberEntity> members = teamMemberRepository.findByTeamId(teamId);
        long memberCount = members.size();

        // 첫 번째 멤버를 개설자로 가정 (실제로는 별도 필드로 관리하는 것이 좋음)
        Integer creatorId = members.isEmpty() ? null : members.get(0).getId().getUuid();

        return TeamDetailResponse.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .subject(null) // TODO: subject 정보 추가
                .time(null) // TODO: time 정보 추가
                .members(new ArrayList<>()) // TODO: 멤버 정보 변환
                .currentMembers((int) memberCount)
                .studyCount(team.getStudyCount())
                .creatorId(creatorId)
                .build();
    }

    /**
     * 스터디 팀 참여
     */
    @Transactional
    public TeamJoinResponse joinTeam(Integer teamId, Integer memberId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 인원 제한 확인
        long currentMembers = teamMemberRepository.countByTeamId(teamId);
        if (currentMembers >= team.getMaximum()) {
            throw new RuntimeException("Team is full");
        }

        // 이미 참여 중인지 확인
        TeamMemberEntity.TeamMemberId id = new TeamMemberEntity.TeamMemberId(teamId, memberId);
        if (teamMemberRepository.existsById(id)) {
            throw new RuntimeException("Already a member of this team");
        }

        // 멤버 조회
        MemberEntity member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 팀원 추가
        TeamMemberEntity teamMember = TeamMemberEntity.builder()
                .id(id)
                .team(team)
                .member(member)
                .build();

        teamMemberRepository.save(teamMember);

        return TeamJoinResponse.builder()
                .teamId(teamId)
                .memberId(memberId)
                .joinedAt(LocalDateTime.now())
                .build();
    }

    /**
     * 스터디 팀 수정
     */
    @Transactional
    public TeamUpdateResponse updateTeam(Integer teamId, TeamUpdateRequest request, Integer requesterId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 개설자 권한 확인 (첫 번째 멤버를 개설자로 가정)
        List<TeamMemberEntity> members = teamMemberRepository.findByTeamId(teamId);
        if (members.isEmpty() || !members.get(0).getId().getUuid().equals(requesterId)) {
            throw new RuntimeException("Only the creator can update the team");
        }

        List<String> updatedFields = new ArrayList<>();

        // 필드 업데이트
        if (request.getDescription() != null) {
            team.setDescription(request.getDescription());
            updatedFields.add("description");
        }

        if (request.getMaximum() != null) {
            // 현재 인원보다 적게 설정하려는 경우 검증
            long currentMembers = teamMemberRepository.countByTeamId(teamId);
            if (request.getMaximum() < currentMembers) {
                throw new RuntimeException("Maximum cannot be less than current members");
            }
            team.setMaximum(request.getMaximum());
            updatedFields.add("maximum");
        }

        if (request.getIsPrivate() != null) {
            team.setIsPrivate(request.getIsPrivate());
            updatedFields.add("isPrivate");
        }

        teamRepository.save(team);

        return TeamUpdateResponse.builder()
                .teamId(teamId)
                .updatedFields(updatedFields)
                .build();
    }

    /**
     * 스터디 팀 종료/삭제
     */
    @Transactional
    public void deleteTeam(Integer teamId, Integer requesterId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 개설자 권한 확인
        List<TeamMemberEntity> members = teamMemberRepository.findByTeamId(teamId);
        if (members.isEmpty() || !members.get(0).getId().getUuid().equals(requesterId)) {
            throw new RuntimeException("Only the creator can delete the team");
        }

        // 진행 중인 세션 확인 (TODO: 세션 테이블 연결 필요)
        if (team.getStatus() == TeamStatus.IN_PROGRESS) {
            throw new RuntimeException("Cannot delete team with active sessions");
        }

        // 팀 상태를 COMPLETED로 변경 (soft delete)
        team.setStatus(TeamStatus.COMPLETED);
        teamRepository.save(team);
    }

    /**
     * 팀 조회 (내부용)
     */
    public TeamEntity findById(Integer teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }
}
