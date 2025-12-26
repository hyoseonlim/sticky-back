package com.sticky.sticky_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultResponse {
    private Integer testId;
    private Integer score;
    private List<Result> results;
    private List<Object> weakItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Integer questionId;
        private Boolean isCorrect;
        private String userAnswer;
        private String correctAnswer;
        private String explanation;
    }
}
