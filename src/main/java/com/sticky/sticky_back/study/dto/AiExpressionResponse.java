package com.sticky.sticky_back.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiExpressionResponse {
    private String korean;
    private String english;
    private String feedback;  // 교정 only) 피드백

    @JsonProperty("exampleEnglish")
    private String exampleEnglish;   // 번역 only) 예문

    @JsonProperty("exampleKorean")
    private String exampleKorean;   // 번역 only) 예문
}
