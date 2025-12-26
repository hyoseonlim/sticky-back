package com.sticky.sticky_back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCreateResponse {
    private Integer testId;
    private Integer reviewId;
    private Integer totalQuestions;
    private List<Question> questions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question {
        private Integer questionId;
        private String type;
        private String questionText;
        private List<String> options;
    }
}
