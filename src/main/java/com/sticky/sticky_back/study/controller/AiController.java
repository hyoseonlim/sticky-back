package com.sticky.sticky_back.study.controller;

import com.sticky.sticky_back.study.dto.AiExpressionResponse;
import com.sticky.sticky_back.study.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    /**
     * 번역 요청 (한->영 또는 영->한)
     * POST /api/ai/translate
     */
    @PostMapping("/translate")
    public ResponseEntity<AiExpressionResponse> translate(@RequestBody Map<String, Object> request) {
        String text = (String) request.get("text");
        Boolean korean = (Boolean) request.getOrDefault("korean", false);

        AiExpressionResponse response = aiService.generateTranslation(text, korean);
        return ResponseEntity.ok(response);
    }

    /**
     * 교정 요청
     * POST /api/ai/feedback
     */
    @PostMapping("/feedback")
    public ResponseEntity<AiExpressionResponse> feedback(@RequestBody Map<String, Object> request) {
        String text = (String) request.get("text");

        AiExpressionResponse response = aiService.generateFeedback(text);
        return ResponseEntity.ok(response);
    }

    /**
     * 화자 분리 스크립트 추출 (내부 API - Mock)
     * POST /api/ai/speaker-diarization
     */
    @PostMapping("/speaker-diarization")
    public ResponseEntity<?> speakerDiarization(@RequestBody Map<String, Object> request) {
        // TODO: STT + 화자 분리 구현 필요
        // 외부 AI 서비스 (Whisper, Pyannote 등) 연동
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
        // TODO: 문법 교정 로직 구현
        // OpenAI API를 활용한 문법 교정 구현 가능
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
     * 복습 문제 자동 생성 (내부 API - Mock)
     * POST /api/ai/generate-questions
     */
    @PostMapping("/generate-questions")
    public ResponseEntity<?> generateQuestions(@RequestBody Map<String, Object> request) {
        // TODO: AI 문제 생성 로직 구현
        // OpenAI API를 활용한 문제 생성 구현 가능
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
