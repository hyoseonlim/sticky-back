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
public class ReviewResponse {
    private Integer reviewId;
    private Integer week;
    private List<ReviewItem> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewItem {
        private Integer itemId;
        private String itemType;
        private Object content;
        private Boolean isMastered;
        private Integer attemptCount;
    }
}
