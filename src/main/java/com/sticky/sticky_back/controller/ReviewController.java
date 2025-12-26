package com.sticky.sticky_back.controller;

import com.sticky.sticky_back.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    /**
     * 개인별 복습자료 생성
     * POST /api/reviews
     */
    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest request) {
        // TODO: 구현 필요
        // 세션 종료 시 자동 생성
        return ResponseEntity.ok(ReviewCreateResponse.builder()
                .reviewId(1)
                .memberId(request.getMemberId())
                .totalItems(15)
                .status("pending")
                .build());
    }

    /**
     * 복습자료 조회
     * GET /api/reviews/{review_id}
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Integer reviewId) {
        // TODO: 구현 필요
        // 1. 본인 확인
        // 2. 개인 복습 항목 목록 조회
        return ResponseEntity.ok(ReviewResponse.builder()
                .reviewId(reviewId)
                .week(1)
                .build());
    }

    /**
     * 복습 테스트 생성
     * POST /api/reviews/{review_id}/test
     */
    @PostMapping("/{reviewId}/test")
    public ResponseEntity<TestCreateResponse> createTest(@PathVariable Integer reviewId) {
        // TODO: 구현 필요
        // 복습 항목 기반 테스트 자동 생성 (AI)
        return ResponseEntity.ok(TestCreateResponse.builder()
                .testId(1)
                .reviewId(reviewId)
                .totalQuestions(10)
                .build());
    }

    /**
     * 테스트 답안 제출
     * POST /api/tests/{test_id}/submit
     */
    @PostMapping("/tests/{testId}/submit")
    public ResponseEntity<TestSubmitResponse> submitTest(
            @PathVariable Integer testId,
            @RequestBody TestSubmitRequest request) {
        // TODO: 구현 필요
        // 1. 답안 채점
        // 2. ReviewItem 업데이트
        return ResponseEntity.ok(TestSubmitResponse.builder()
                .testId(testId)
                .score(80)
                .correctAnswers(8)
                .totalQuestions(10)
                .build());
    }

    /**
     * 테스트 결과 조회
     * GET /api/tests/{test_id}/result
     */
    @GetMapping("/tests/{testId}/result")
    public ResponseEntity<TestResultResponse> getTestResult(@PathVariable Integer testId) {
        // TODO: 구현 필요
        // 테스트 결과 상세 조회 (틀린 문제 해설 제공)
        return ResponseEntity.ok(TestResultResponse.builder()
                .testId(testId)
                .score(80)
                .build());
    }
}
