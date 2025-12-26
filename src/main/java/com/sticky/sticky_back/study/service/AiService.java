package com.sticky.sticky_back.study.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sticky.sticky_back.study.config.AiConfig;
import com.sticky.sticky_back.study.dto.AiExpressionResponse;
import com.sticky.sticky_back.study.dto.AiRequest;
import com.sticky.sticky_back.study.dto.AiResponse;
import com.sticky.sticky_back.study.util.PromptLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

    private final AiConfig aiConfig;
    private final RestTemplate restTemplate;
    private final PromptLoader promptLoader;

    /**
     * 번역 생성 (한->영 또는 영->한)
     */
    public AiExpressionResponse generateTranslation(String question, boolean korean) {
        try {
            String prompt = promptLoader.createTranslationPrompt(question, korean);
            String response = callUpstageAi(prompt);
            return parseExpressionResponse(response);

        } catch (Exception e) {
            log.error("Failed to generate translation for question: {}", question, e);
            throw new RuntimeException("AI translation service failed", e);
        }
    }

    /**
     * 교정 피드백 생성
     */
    public AiExpressionResponse generateFeedback(String question) {
        try {
            String prompt = promptLoader.createFeedbackPrompt(question);
            String response = callUpstageAi(prompt);
            return parseExpressionResponse(response);

        } catch (Exception e) {
            log.error("Failed to generate feedback for question: {}", question, e);
            throw new RuntimeException("AI feedback service failed", e);
        }
    }

    /**
     * Upstage Solar API 호출
     */
    private String callUpstageAi(String prompt) {
        String url = aiConfig.getBaseUrl() + "/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(aiConfig.getApiKey());

        AiRequest request = AiRequest.builder()
                .model(aiConfig.getModel())
                .maxTokens(aiConfig.getMaxTokens())
                .messages(List.of(
                        AiRequest.Message.builder()
                                .role("user")
                                .content(prompt)
                                .build()
                ))
                .build();

        HttpEntity<AiRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<AiResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, AiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getChoices().get(0).getMessage().getContent();
            } else {
                throw new RuntimeException("Upstage AI API returned unsuccessful response");
            }

        } catch (HttpClientErrorException e) {
            log.error("Upstage AI API client error: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Upstage AI API client error: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("Upstage AI API server error: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Upstage AI API server error: " + e.getMessage(), e);
        }
    }

    /**
     * AI 응답 파싱 (Expression)
     */
    private AiExpressionResponse parseExpressionResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = extractJsonFromResponse(response);
            return objectMapper.readValue(jsonResponse, AiExpressionResponse.class);

        } catch (Exception e) {
            log.error("Failed to parse AI response: {}", response, e);
            return null;
        }
    }

    /**
     * 응답에서 JSON 추출
     */
    private String extractJsonFromResponse(String response) {
        log.debug("Extracting JSON from response: {}", response);
        response = response.trim();

        // 배열인 경우
        int arrayStart = response.indexOf('[');
        int arrayEnd = response.lastIndexOf(']');
        if (arrayStart != -1 && arrayEnd != -1 && arrayEnd > arrayStart) {
            return response.substring(arrayStart, arrayEnd + 1);
        }

        // 객체인 경우
        int objectStart = response.indexOf('{');
        int objectEnd = response.lastIndexOf('}');
        if (objectStart != -1 && objectEnd != -1 && objectEnd > objectStart) {
            return response.substring(objectStart, objectEnd + 1);
        }

        throw new RuntimeException("No valid JSON found in response");
    }
}
