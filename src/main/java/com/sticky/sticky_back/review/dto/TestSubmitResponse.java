package com.sticky.sticky_back.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmitResponse {
    private Integer testId;
    private Integer score;
    private Integer correctAnswers;
    private Integer totalQuestions;
    private LocalDateTime completedAt;
}
