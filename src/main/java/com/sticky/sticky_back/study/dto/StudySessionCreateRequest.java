package com.sticky.sticky_back.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionCreateRequest {
    private Integer teamId;
    private Integer week;
    private LocalDate scheduledDate;
}
