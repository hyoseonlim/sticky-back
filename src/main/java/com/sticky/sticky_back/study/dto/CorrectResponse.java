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
public class CorrectResponse {
    private String requestId;
    private String correctedText;
    private String feedback;
    private List<String> examples;
}
