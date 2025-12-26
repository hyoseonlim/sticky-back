package com.sticky.sticky_back.study.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionRedis {

    private Integer expressionId;
    private String english;
    private String korean;
    private String exampleEnglish;  // 번역 only
    private String exampleKorean;   // 번역 only
    private String original;        // 교정 only
    private String feedback;        // 교정 only
    private boolean translation;
}
