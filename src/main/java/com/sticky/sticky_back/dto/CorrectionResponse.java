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
public class CorrectionResponse {
    private Integer sessionId;
    private List<Correction> corrections;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Correction {
        private String speakerId;
        private String original;
        private String corrected;
        private String feedback;
        private String example;
    }
}
