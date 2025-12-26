package com.sticky.sticky_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WrongAnswerResponse {
    private Integer sessionId;
    private String answerId;
    private Boolean savedToRedis;
}
