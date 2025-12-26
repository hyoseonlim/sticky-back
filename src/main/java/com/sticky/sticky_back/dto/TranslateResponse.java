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
public class TranslateResponse {
    private String requestId;
    private String translatedText;
    private String explanation;
    private List<String> examples;
}
