package com.sticky.sticky_back.team.controller;

import com.sticky.sticky_back.team.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    /**
     * 스터디 개설
     * POST /api/teams
     */
    @PostMapping
    public ResponseEntity<TeamCreateResponse> createTeam(@RequestBody TeamCreateRequest request) {
        // TODO: 구현 필요
        // 1. 스터디 팀 생성
        // 2. 개설자 자동 팀원 추가
        return ResponseEntity.ok(TeamCreateResponse.builder()
                .teamId(1)
                .name(request.getName())
                .creatorId(1)
                .status("recruiting")
                .currentMembers(1)
                .build());
    }

    /**
     * 스터디 목록 조회
     * GET /api/teams
     */
    @GetMapping
    public ResponseEntity<TeamListResponse> getTeams(
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) Boolean isOnline,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: 구현 필요
        // 1. 조건에 맞는 스터디 검색 (페이징)
        // 2. 모집 중인 팀만 조회
        return ResponseEntity.ok(TeamListResponse.builder().build());
    }

    /**
     * 스터디 상세 조회
     * GET /api/teams/{team_id}
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponse> getTeamDetail(@PathVariable Integer teamId) {
        // TODO: 구현 필요
        // 스터디 팀 상세 정보 조회
        return ResponseEntity.ok(TeamDetailResponse.builder()
                .teamId(teamId)
                .build());
    }

    /**
     * 스터디 참여 신청
     * POST /api/teams/{team_id}/join
     */
    @PostMapping("/{teamId}/join")
    public ResponseEntity<TeamJoinResponse> joinTeam(
            @PathVariable Integer teamId,
            @RequestBody TeamJoinRequest request) {
        // TODO: 구현 필요
        // 1. 인원 제한 확인
        // 2. 팀원 추가
        return ResponseEntity.ok(TeamJoinResponse.builder()
                .teamId(teamId)
                .memberId(request.getMemberId())
                .build());
    }

    /**
     * 스터디 팀 수정
     * PUT /api/teams/{team_id}
     */
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamUpdateResponse> updateTeam(
            @PathVariable Integer teamId,
            @RequestBody TeamUpdateRequest request) {
        // TODO: 구현 필요
        // 1. 개설자 권한 확인
        // 2. 스터디 팀 정보 수정
        return ResponseEntity.ok(TeamUpdateResponse.builder()
                .teamId(teamId)
                .build());
    }

    /**
     * 스터디 팀 종료
     * DELETE /api/teams/{team_id}
     */
    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId) {
        // TODO: 구현 필요
        // 1. 진행 중인 세션 확인
        // 2. 스터디 팀 종료/삭제
        return ResponseEntity.ok().body("{\"team_id\": " + teamId + ", \"status\": \"completed\"}");
    }
}
