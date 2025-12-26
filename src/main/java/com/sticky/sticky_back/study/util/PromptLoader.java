package com.sticky.sticky_back.study.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PromptLoader {

    private final ConcurrentHashMap<String, String> promptCache = new ConcurrentHashMap<>();

    /**
     * 번역 프롬프트 생성 (한->영 또는 영->한)
     */
    public String createTranslationPrompt(String question, boolean korean) {
        String promptTemplate = loadPrompt(korean ? "translation-to-english.txt" : "translation-to-korean.txt");
        return formatPrompt(promptTemplate, question);
    }

    /**
     * 교정 피드백 프롬프트 생성
     */
    public String createFeedbackPrompt(String question) {
        return formatPrompt(loadPrompt("feedback.txt"), question);
    }

    /**
     * 프롬프트 파일 로드 (캐싱)
     */
    private String loadPrompt(String promptFileName) {
        return promptCache.computeIfAbsent(promptFileName, this::readPromptFromFile);
    }

    /**
     * 프롬프트 파일 읽기
     */
    private String readPromptFromFile(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("prompts/" + fileName);
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to load prompt file: {}", fileName, e);
            throw new RuntimeException("Failed to load prompt template: " + fileName, e);
        }
    }

    /**
     * 프롬프트 템플릿 포맷팅
     */
    private String formatPrompt(String promptTemplate, Object... args) {
        return String.format(promptTemplate, args);
    }
}
