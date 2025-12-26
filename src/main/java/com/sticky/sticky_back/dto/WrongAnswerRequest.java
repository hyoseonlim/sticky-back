package com.sticky.sticky_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WrongAnswerRequest {
    private Integer memberId;
    private String question;
    private String wrongAnswer;
    private String correctAnswer;
    private String explanation;
}
