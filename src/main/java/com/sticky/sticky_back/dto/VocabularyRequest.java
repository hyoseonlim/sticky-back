package com.sticky.sticky_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyRequest {
    private Integer memberId;
    private String english;
    private String korean;
    private String example;
}
