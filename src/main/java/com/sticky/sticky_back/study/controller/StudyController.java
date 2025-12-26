package com.sticky.sticky_back.study.controller;

import com.sticky.sticky_back.study.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    /**
     * 스터디 세션 생성 (스터디룸 입장)
     * POST /api/study/teams/{team_id}/{session}
     */
    @PostMapping("/teams/{teamId}/{session}")
    public ResponseEntity<StudySessionCreateResponse> createSession(
            @PathVariable Integer teamId,
            @PathVariable Integer session,
            @RequestBody StudySessionCreateRequest request) {
        // TODO: 구현 필요
        // 1. 세션 존재 확인 (Redis)
        // 2. 없으면 생성, 있으면 기존 ID 반환
        return ResponseEntity.ok(StudySessionCreateResponse.builder()
                .studyroomId(1)
                .build());
    }

    /**
     * 녹음 파일 업로드
     * POST /api/study/{study_id}/recording
     */
    @PostMapping("/{studyId}/recording")
    public ResponseEntity<RecordingUploadResponse> uploadRecording(
            @PathVariable Integer studyId,
            @RequestParam("audio_file") MultipartFile audioFile) {
        // TODO: 구현 필요
        // 1. 파일 S3 업로드
        // 2. AI 화자 분리 비동기 처리 요청
        return ResponseEntity.ok(RecordingUploadResponse.builder()
                .sessionId(studyId)
                .recordingUrl("s3://...")
                .processingStatus("pending")
                .estimatedTime("2-3 minutes")
                .build());
    }

    /**
     * 화자 분리 스크립트 추출
     * GET /api/study/{study_id}/transcript
     */
    @GetMapping("/{studyId}/transcript")
    public ResponseEntity<TranscriptResponse> getTranscript(@PathVariable Integer studyId) {
        // TODO: 구현 필요
        // AI 처리 완료 후 화자별 스크립트 조회
        return ResponseEntity.ok(TranscriptResponse.builder()
                .sessionId(studyId)
                .processingStatus("completed")
                .build());
    }

    /**
     * 문법 교정 결과 조회
     * GET /api/study/{study_id}/corrections
     */
    @GetMapping("/{studyId}/corrections")
    public ResponseEntity<CorrectionResponse> getCorrections(@PathVariable Integer studyId) {
        // TODO: 구현 필요
        // 교정이 필요한 표현 목록 조회 (화자별 그룹핑)
        return ResponseEntity.ok(CorrectionResponse.builder()
                .sessionId(studyId)
                .build());
    }

    /**
     * 단어 등록
     * POST /api/sessions/{session_id}/vocabulary
     */
    @PostMapping("/sessions/{sessionId}/vocabulary")
    public ResponseEntity<VocabularyResponse> addVocabulary(
            @PathVariable Integer sessionId,
            @RequestBody VocabularyRequest request) {
        // TODO: 구현 필요
        // Redis에 임시 저장
        return ResponseEntity.ok(VocabularyResponse.builder()
                .sessionId(sessionId)
                .vocabId("temp_123")
                .savedToRedis(true)
                .build());
    }

    /**
     * 오답 등록
     * POST /api/sessions/{session_id}/wrong-answer
     */
    @PostMapping("/sessions/{sessionId}/wrong-answer")
    public ResponseEntity<WrongAnswerResponse> addWrongAnswer(
            @PathVariable Integer sessionId,
            @RequestBody WrongAnswerRequest request) {
        // TODO: 구현 필요
        // Redis에 임시 저장
        return ResponseEntity.ok(WrongAnswerResponse.builder()
                .sessionId(sessionId)
                .answerId("temp_456")
                .savedToRedis(true)
                .build());
    }

    /**
     * 번역 요청
     * POST /api/sessions/{session_id}/translate
     */
    @PostMapping("/sessions/{sessionId}/translate")
    public ResponseEntity<TranslateResponse> translate(
            @PathVariable Integer sessionId,
            @RequestBody TranslateRequest request) {
        // TODO: 구현 필요
        // AI 번역 + 설명
        return ResponseEntity.ok(TranslateResponse.builder()
                .requestId("temp_789")
                .translatedText("translated text")
                .build());
    }

    /**
     * 교정 요청
     * POST /api/sessions/{session_id}/correct
     */
    @PostMapping("/sessions/{sessionId}/correct")
    public ResponseEntity<CorrectResponse> correct(
            @PathVariable Integer sessionId,
            @RequestBody CorrectRequest request) {
        // TODO: 구현 필요
        // 자연스러운 표현 제안
        return ResponseEntity.ok(CorrectResponse.builder()
                .requestId("temp_101")
                .correctedText("corrected text")
                .build());
    }

    /**
     * 스터디 세션 종료
     * POST /api/sessions/{session_id}/end
     */
    @PostMapping("/sessions/{sessionId}/end")
    public ResponseEntity<SessionEndResponse> endSession(@PathVariable Integer sessionId) {
        // TODO: 구현 필요
        // 1. Redis → MongoDB 영구 저장
        // 2. 개인 복습자료 생성
        return ResponseEntity.ok(SessionEndResponse.builder()
                .sessionId(sessionId)
                .status("completed")
                .reportId(1)
                .reviewGenerated(true)
                .build());
    }
}
