package com.sticky.sticky_back.study.controller;

import com.sticky.sticky_back.study.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    /**
     * 보고서 조회
     * GET /api/reports/{report_id}
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Integer reportId) {
        // TODO: 구현 필요
        // 1. 팀원 권한 확인
        // 2. 스터디 세션 보고서 조회
        return ResponseEntity.ok(ReportResponse.builder()
                .reportId(reportId)
                .sessionId(1)
                .week(1)
                .summary("스터디 요약")
                .build());
    }
}
