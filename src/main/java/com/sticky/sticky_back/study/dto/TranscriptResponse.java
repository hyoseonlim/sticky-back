package com.sticky.sticky_back.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptResponse {
    private Integer sessionId;
    private String processingStatus;
    private List<Speaker> speakers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Speaker {
        private String speakerId;
        private Integer memberId;
        private List<Segment> segments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Segment {
        private String timestamp;
        private String text;
    }
}
