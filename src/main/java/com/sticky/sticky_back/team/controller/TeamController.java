package com.sticky.sticky_back.team.controller;

import com.sticky.sticky_back.team.dto.*;
import com.sticky.sticky_back.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    /**
     * 스터디 개설
     * POST /api/teams
     */
    @PostMapping
    public ResponseEntity<TeamCreateResponse> createTeam(
            @RequestBody TeamCreateRequest request,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "1") Integer userId) {
        TeamCreateResponse response = teamService.createTeam(request, userId);
        return ResponseEntity.ok(response);
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
        TeamListResponse response = teamService.getTeams(subjectId, day, time, isOnline, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * 스터디 상세 조회
     * GET /api/teams/{team_id}
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponse> getTeamDetail(@PathVariable Integer teamId) {
        TeamDetailResponse response = teamService.getTeamDetail(teamId);
        return ResponseEntity.ok(response);
    }

    /**
     * 스터디 참여 신청
     * POST /api/teams/{team_id}/join
     */
    @PostMapping("/{teamId}/join")
    public ResponseEntity<TeamJoinResponse> joinTeam(
            @PathVariable Integer teamId,
            @RequestBody TeamJoinRequest request) {
        TeamJoinResponse response = teamService.joinTeam(teamId, request.getMemberId());
        return ResponseEntity.ok(response);
    }

    /**
     * 스터디 팀 수정
     * PUT /api/teams/{team_id}
     */
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamUpdateResponse> updateTeam(
            @PathVariable Integer teamId,
            @RequestBody TeamUpdateRequest request,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "1") Integer userId) {
        TeamUpdateResponse response = teamService.updateTeam(teamId, request, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 스터디 팀 종료
     * DELETE /api/teams/{team_id}
     */
    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(
            @PathVariable Integer teamId,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "1") Integer userId) {
        teamService.deleteTeam(teamId, userId);
        return ResponseEntity.ok().body("{\"team_id\": " + teamId + ", \"status\": \"completed\"}");
    }
}
