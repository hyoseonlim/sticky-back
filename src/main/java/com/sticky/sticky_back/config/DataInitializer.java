package com.sticky.sticky_back.config;

import com.sticky.sticky_back.team.datamodel.TeamEntity;
import com.sticky.sticky_back.team.datamodel.TeamMemberEntity;
import com.sticky.sticky_back.team.datamodel.TeamStatus;
import com.sticky.sticky_back.team.repository.TeamMemberRepository;
import com.sticky.sticky_back.team.repository.TeamRepository;
import com.sticky.sticky_back.user.datamodel.MemberEntity;
import com.sticky.sticky_back.user.datamodel.MemberLevel;
import com.sticky.sticky_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 이미 존재하는지 확인
        if (userRepository.count() > 0) {
            log.info("초기 데이터가 이미 존재합니다. 데이터 삽입을 건너뜁니다.");
            return;
        }

        log.info("초기 데이터 삽입 시작...");

        // 회원 5명 생성
        List<MemberEntity> members = createMembers();
        log.info("회원 {} 명 생성 완료", members.size());

        // 팀 3개 생성
        List<TeamEntity> teams = createTeams();
        log.info("팀 {} 개 생성 완료", teams.size());

        // 팀원 배정
        assignMembersToTeams(members, teams);
        log.info("팀원 배정 완료");

        log.info("초기 데이터 삽입 완료!");
    }

    private List<MemberEntity> createMembers() {
        List<MemberEntity> members = new ArrayList<>();

        // 회원 1
        MemberEntity member1 = MemberEntity.builder()
                .nickname("Hyo")
                .email("student1@example.com")
                .password("password123")
                .name("김철수")
                .tel("010-1111-1111")
                .level(MemberLevel.LEVEL_1)
                .rate(0.0)
                .build();
        members.add(userRepository.save(member1));

        // 회원 2
        MemberEntity member2 = MemberEntity.builder()
                .nickname("Tanny")
                .email("student2@example.com")
                .password("password123")
                .name("이영희")
                .tel("010-2222-2222")
                .level(MemberLevel.LEVEL_2)
                .rate(75.5)
                .build();
        members.add(userRepository.save(member2));

        // 회원 3
        MemberEntity member3 = MemberEntity.builder()
                .nickname("Minu")
                .email("student3@example.com")
                .password("password123")
                .name("박민수")
                .tel("010-3333-3333")
                .level(MemberLevel.LEVEL_3)
                .rate(85.0)
                .build();
        members.add(userRepository.save(member3));

        // 회원 4
        MemberEntity member4 = MemberEntity.builder()
                .nickname("Dana")
                .email("student4@example.com")
                .password("password123")
                .name("정지현")
                .tel("010-4444-4444")
                .level(MemberLevel.LEVEL_2)
                .rate(70.0)
                .build();
        members.add(userRepository.save(member4));

        // 회원 5
        MemberEntity member5 = MemberEntity.builder()
                .nickname("Yoon")
                .email("student5@example.com")
                .password("password123")
                .name("최동욱")
                .tel("010-5555-5555")
                .level(MemberLevel.LEVEL_1)
                .rate(65.5)
                .build();
        members.add(userRepository.save(member5));

        return members;
    }

    private List<TeamEntity> createTeams() {
        List<TeamEntity> teams = new ArrayList<>();

        // 팀 1: TOEIC 스터디
        TeamEntity team1 = TeamEntity.builder()
                .name("토익 900점 달성반")
                .description("토익 900점 이상을 목표로 하는 스터디입니다.")
                .year(2025)
                .studyCount(0)
                .isPrivate(false)
                .minimum(2)
                .maximum(5)
                .status(TeamStatus.RECRUITING)
                .isOnline(true)
                .location(null)
                .build();
        teams.add(teamRepository.save(team1));

        // 팀 2: 자유회화 스터디
        TeamEntity team2 = TeamEntity.builder()
                .name("영어 자유회화 모임")
                .description("매주 자유주제로 영어 회화를 연습합니다.")
                .year(2025)
                .studyCount(3)
                .isPrivate(false)
                .minimum(3)
                .maximum(6)
                .status(TeamStatus.IN_PROGRESS)
                .isOnline(false)
                .location("서울시 강남구 스터디룸")
                .build();
        teams.add(teamRepository.save(team2));

        // 팀 3: OPIC 준비반
        TeamEntity team3 = TeamEntity.builder()
                .name("OPIC IH 목표반")
                .description("OPIC IH 등급을 목표로 준비하는 스터디입니다.")
                .year(2025)
                .studyCount(1)
                .isPrivate(true)
                .minimum(2)
                .maximum(4)
                .status(TeamStatus.RECRUITING)
                .isOnline(true)
                .location(null)
                .build();
        teams.add(teamRepository.save(team3));

        return teams;
    }

    private void assignMembersToTeams(List<MemberEntity> members, List<TeamEntity> teams) {
        // 팀 1 (토익): 회원 1, 2
        addMemberToTeam(members.get(0), teams.get(0));
        addMemberToTeam(members.get(1), teams.get(0));

        // 팀 2 (자유회화): 회원 2, 3, 4
        addMemberToTeam(members.get(1), teams.get(1));
        addMemberToTeam(members.get(2), teams.get(1));
        addMemberToTeam(members.get(3), teams.get(1));

        // 팀 3 (OPIC): 회원 4, 5
        addMemberToTeam(members.get(3), teams.get(2));
        addMemberToTeam(members.get(4), teams.get(2));
    }

    private void addMemberToTeam(MemberEntity member, TeamEntity team) {
        TeamMemberEntity.TeamMemberId id = new TeamMemberEntity.TeamMemberId(
                team.getTeamId(),
                member.getUuid()
        );

        TeamMemberEntity teamMember = TeamMemberEntity.builder()
                .id(id)
                .team(team)
                .member(member)
                .build();

        teamMemberRepository.save(teamMember);
    }
}
