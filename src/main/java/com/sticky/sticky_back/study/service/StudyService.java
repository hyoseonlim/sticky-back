package com.sticky.sticky_back.study.service;

import com.sticky.sticky_back.study.datamodel.*;
import com.sticky.sticky_back.study.dto.*;
import com.sticky.sticky_back.study.repository.ReportRepository;
import com.sticky.sticky_back.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyService {

    private final StudyRepository studyRepository;
    private final ReportRepository reportRepository;
    private final AiService aiService;

    /**
     * 스터디 룸 입장 (세션 생성 또는 기존 세션 반환)
     */
    @Transactional
    public StudyEnterDto getStudyRoom(Integer teamId, Boolean isGeneral) {
        StudyEnterDto studyEnterDto = new StudyEnterDto();
        studyEnterDto.setIsGeneral(isGeneral);

        // 이미 진행 중인 스터디가 있는지 확인
        StudyRedis existingStudy = studyRepository.findByTeamId(teamId).orElse(null);
        if (existingStudy != null) {
            studyEnterDto.setStudyId(existingStudy.getId());
            return studyEnterDto;
        }

        log.info("team {}의 study redis 생성 시작", teamId);

        // 새 스터디 세션 생성
        String studyId = UUID.randomUUID().toString();

        // 보고서 생성 (주차 계산)
        long weekNumber = reportRepository.countByTeamId(teamId) + 1;
        ReportDocument report = ReportDocument.builder()
                .id(studyId)
                .teamId(teamId)
                .week((int) weekNumber)
                .isSubmitted(false)
                .build();
        reportRepository.save(report);

        // 일반 과목 vs 회화 과목에 따라 다른 초기화
        if (isGeneral) {
            GeneralRedis general = GeneralRedis.builder()
                    .id("general_1")
                    .corrections(new ArrayList<>())
                    .vocabs(new ArrayList<>())
                    .expressions(new ArrayList<>())
                    .build();

            StudyRedis studyRedis = StudyRedis.builder()
                    .id(studyId)
                    .teamId(teamId)
                    .topics(null)
                    .general(general)
                    .build();
            studyRepository.save(studyRedis);
        } else {
            StudyRedis studyRedis = StudyRedis.builder()
                    .id(studyId)
                    .teamId(teamId)
                    .topics(new ArrayList<>())
                    .general(null)
                    .build();
            studyRepository.save(studyRedis);
        }

        studyEnterDto.setStudyId(studyId);
        return studyEnterDto;
    }

    /**
     * 스터디 데이터 조회
     */
    public StudyRedis getStudyData(String studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("Study not found for id: " + studyId));
    }

    /**
     * 회화 과목: 토픽 추가
     */
    @Transactional
    public StudyRedis addTopicToStudy(String studyId, List<TopicDto> topicDtos) {
        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        if (study.getTopics() == null) {
            study.setTopics(new ArrayList<>());
        }

        for (TopicDto dto : topicDtos) {
            TopicRedis topic = TopicRedis.builder()
                    .topicId(study.getTopics().size() + 1)
                    .topic(dto.getTopic())
                    .category(dto.getCategory())
                    .expressions(new ArrayList<>())
                    .build();
            study.getTopics().add(topic);
        }

        studyRepository.save(study);
        return study;
    }

    /**
     * 회화 과목: AI 도움 받기 (번역/교정)
     */
    @Transactional
    public StudyRedis getAiHelpAndAdd(String studyId, ExpressionToAskDto questionDto) {
        validateInput(studyId, questionDto);

        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        TopicRedis targetTopic = findTopicInStudy(study, questionDto.getTopicId());

        // AI 서비스 호출 - Upstage Solar API를 통한 번역 또는 교정
        AiExpressionResponse aiResponse = questionDto.isTranslation() ?
                aiService.generateTranslation(questionDto.getQuestion(), questionDto.isKorean()) :
                aiService.generateFeedback(questionDto.getQuestion());

        ExpressionRedis expression = createExpression(targetTopic, questionDto, aiResponse);
        targetTopic.getExpressions().add(expression);

        studyRepository.save(study);

        log.info("Successfully added AI-generated expression for study: {}, topic: {}",
                studyId, questionDto.getTopicId());

        return study;
    }

    /**
     * 일반 과목: 오답 추가
     */
    @Transactional
    public StudyRedis addCorrectionsToGeneralStudy(String studyId, List<CorrectionRedis> correctionDtos) {
        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        GeneralRedis general = study.getGeneral();
        if (general == null) {
            throw new IllegalArgumentException("This is not a general subject study");
        }

        if (general.getCorrections() == null) {
            general.setCorrections(new ArrayList<>());
        }

        for (CorrectionRedis dto : correctionDtos) {
            CorrectionRedis correction = CorrectionRedis.builder()
                    .id("correction_" + System.currentTimeMillis() + "_" + Math.random())
                    .question(dto.getQuestion())
                    .answer(dto.getAnswer())
                    .description(dto.getDescription())
                    .build();
            general.getCorrections().add(correction);
        }

        studyRepository.save(study);
        return study;
    }

    /**
     * 일반 과목: 단어 추가
     */
    @Transactional
    public StudyRedis addVocabsToGeneralStudy(String studyId, List<VocabRedis> vocabDtos) {
        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        GeneralRedis general = study.getGeneral();
        if (general == null) {
            throw new IllegalArgumentException("This is not a general subject study");
        }

        if (general.getVocabs() == null) {
            general.setVocabs(new ArrayList<>());
        }

        for (VocabRedis dto : vocabDtos) {
            VocabRedis vocab = VocabRedis.builder()
                    .id("vocab_" + System.currentTimeMillis() + "_" + Math.random())
                    .english(dto.getEnglish())
                    .korean(dto.getKorean())
                    .build();
            general.getVocabs().add(vocab);
        }

        studyRepository.save(study);
        return study;
    }

    /**
     * 일반 과목: AI 도움 받기 (번역/교정)
     */
    @Transactional
    public StudyRedis getGeneralAiHelpAndAdd(String studyId, ExpressionToAskDto questionDto) {
        validateGeneralInput(studyId, questionDto);

        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        GeneralRedis general = study.getGeneral();
        if (general == null) {
            throw new IllegalArgumentException("This is not a general subject study");
        }

        if (general.getExpressions() == null) {
            general.setExpressions(new ArrayList<>());
        }

        // AI 서비스 호출 - Upstage Solar
        AiExpressionResponse aiResponse = questionDto.isTranslation() ?
                aiService.generateTranslation(questionDto.getQuestion(), questionDto.isKorean()) :
                aiService.generateFeedback(questionDto.getQuestion());

        ExpressionRedis expression = createGeneralExpression(general, questionDto, aiResponse);
        general.getExpressions().add(expression);

        studyRepository.save(study);

        log.info("Successfully added AI-generated expression for general study: {}", studyId);

        return study;
    }

    /**
     * 스터디 종료 (Redis → MongoDB 영구 저장)
     */
    @Transactional
    public String finishStudy(String studyId) {
        StudyRedis study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for id: " + studyId));

        ReportDocument report = reportRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found for id: " + studyId));

        // 회화 과목 vs 일반 과목 구분
        if (study.getTopics() != null && !study.getTopics().isEmpty()) {
            // 회화 과목: topics 데이터를 JSON으로 변환하여 저장
            // TODO: topics를 적절한 형식으로 변환
            report.setContents("Speaking topics: " + study.getTopics().size());
        } else if (study.getGeneral() != null) {
            // 일반 과목: general 데이터를 JSON으로 변환하여 저장
            GeneralRedis general = study.getGeneral();
            report.setContents("General - Corrections: " + general.getCorrections().size() +
                    ", Vocabs: " + general.getVocabs().size() +
                    ", Expressions: " + general.getExpressions().size());
        }

        reportRepository.save(report);

        // Redis에서 삭제
        studyRepository.deleteById(studyId);

        log.info("Study finished and saved to MongoDB: {}", studyId);

        return studyId;
    }

    /**
     * 보고서 조회
     */
    public ReportDocument getReport(String reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found for id: " + reportId));
    }

    /**
     * 보고서 제출
     */
    @Transactional
    public String submitReport(String reportId) {
        ReportDocument report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found for id: " + reportId));

        report.setIsSubmitted(true);
        reportRepository.save(report);

        // TODO: 개인 복습자료 생성 로직

        log.info("Report submitted: {}", reportId);

        return reportId;
    }

    // ==================== Private Helper Methods ====================

    private void validateInput(String studyId, ExpressionToAskDto questionDto) {
        if (studyId == null || studyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Study ID cannot be null or empty");
        }
        if (questionDto == null) {
            throw new IllegalArgumentException("Question DTO cannot be null");
        }
        if (questionDto.getQuestion() == null || questionDto.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
        if (questionDto.getTopicId() == null) {
            throw new IllegalArgumentException("Topic ID cannot be null");
        }
    }

    private void validateGeneralInput(String studyId, ExpressionToAskDto questionDto) {
        if (studyId == null || studyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Study ID cannot be null or empty");
        }
        if (questionDto == null) {
            throw new IllegalArgumentException("Question DTO cannot be null");
        }
        if (questionDto.getQuestion() == null || questionDto.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
    }

    private TopicRedis findTopicInStudy(StudyRedis study, Integer topicId) {
        return study.getTopics().stream()
                .filter(t -> t.getTopicId().equals(topicId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Topic not found in study"));
    }

    private ExpressionRedis createExpression(TopicRedis topic, ExpressionToAskDto questionDto,
                                             AiExpressionResponse aiResponse) {
        Integer newExpressionId = topic.getExpressions().stream()
                .mapToInt(ExpressionRedis::getExpressionId)
                .max()
                .orElse(0) + 1;

        ExpressionRedis expression = ExpressionRedis.builder()
                .expressionId(newExpressionId)
                .korean(aiResponse.getKorean())
                .english(aiResponse.getEnglish())
                .translation(questionDto.isTranslation())
                .build();

        if (questionDto.isTranslation()) {
            expression.setExampleEnglish(aiResponse.getExampleEnglish());
            expression.setExampleKorean(aiResponse.getExampleKorean());
        } else {
            expression.setFeedback(aiResponse.getFeedback());
            expression.setOriginal(questionDto.getQuestion());
        }

        return expression;
    }

    private ExpressionRedis createGeneralExpression(GeneralRedis general, ExpressionToAskDto questionDto,
                                                    AiExpressionResponse aiResponse) {
        Integer newExpressionId = general.getExpressions().stream()
                .mapToInt(ExpressionRedis::getExpressionId)
                .max()
                .orElse(0) + 1;

        ExpressionRedis expression = ExpressionRedis.builder()
                .expressionId(newExpressionId)
                .korean(aiResponse.getKorean())
                .english(aiResponse.getEnglish())
                .translation(questionDto.isTranslation())
                .build();

        if (questionDto.isTranslation()) {
            expression.setExampleEnglish(aiResponse.getExampleEnglish());
            expression.setExampleKorean(aiResponse.getExampleKorean());
        } else {
            expression.setFeedback(aiResponse.getFeedback());
            expression.setOriginal(questionDto.getQuestion());
        }

        return expression;
    }
}
