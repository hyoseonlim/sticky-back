package com.sticky.sticky_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    /**
     * 화자 분리 스크립트 추출 (내부 API - Mock)
     * POST /api/ai/speaker-diarization
     */
    @PostMapping("/speaker-diarization")
    public ResponseEntity<?> speakerDiarization(@RequestBody Map<String, Object> request) {
        // TODO: Mock 데이터 반환
        // STT + 화자 분리 (더미 데이터)
        return ResponseEntity.ok(Map.of(
            "speakers", List.of(
                Map.of(
                    "speaker_id", "A",
                    "segments", List.of(
                        Map.of(
                            "start", 0.0,
                            "end", 5.2,
                            "text", "Hello, how are you?"
                        )
                    )
                )
            )
        ));
    }

    /**
     * 문법 교정 및 표현 제안 (내부 API - Mock)
     * POST /api/ai/grammar-correction
     */
    @PostMapping("/grammar-correction")
    public ResponseEntity<?> grammarCorrection(@RequestBody Map<String, Object> request) {
        // TODO: Mock 데이터 반환
        // LLM 기반 문법 교정 (더미 데이터)
        return ResponseEntity.ok(Map.of(
            "corrections", List.of(
                Map.of(
                    "original", "I go to school yesterday",
                    "corrected", "I went to school yesterday",
                    "type", "grammar",
                    "feedback", "과거 시제 사용",
                    "example", "I went shopping last week."
                )
            )
        ));
    }

    /**
     * 번역 및 설명 (내부 API - Mock)
     * POST /api/ai/translate
     */
    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody Map<String, Object> request) {
        // TODO: Mock 데이터 반환
        // AI 번역 + 문맥 설명 (더미 데이터)
        return ResponseEntity.ok(Map.of(
            "translated", "Translated text",
            "explanation", "Translation explanation",
            "examples", List.of("Example 1", "Example 2")
        ));
    }

    /**
     * 복습 문제 자동 생성 (내부 API - Mock)
     * POST /api/ai/generate-questions
     */
    @PostMapping("/generate-questions")
    public ResponseEntity<?> generateQuestions(@RequestBody Map<String, Object> request) {
        // TODO: Mock 데이터 반환
        // AI 문제 생성 (더미 데이터)
        return ResponseEntity.ok(Map.of(
            "questions", List.of(
                Map.of(
                    "type", "multiple_choice",
                    "question", "Choose the correct past tense",
                    "options", List.of("go", "went", "gone", "going"),
                    "correct_answer", "went"
                )
            )
        ));
    }
}
