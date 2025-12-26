package com.sticky.sticky_back.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionEndResponse {
    private Integer sessionId;
    private String status;
    private Integer reportId;
    private Boolean reviewGenerated;
}
