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
public class ReportResponse {
    private Integer reportId;
    private Integer sessionId;
    private Integer week;
    private String summary;
    private Object conversationScript;
    private List<Object> vocabularies;
    private List<Object> wrongAnswers;
    private List<Object> aiRequests;
}
