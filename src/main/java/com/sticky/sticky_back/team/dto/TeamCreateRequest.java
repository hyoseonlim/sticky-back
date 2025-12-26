package com.sticky.sticky_back.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateRequest {
    private String name;
    private Integer subjectId;
    private String description;
    private Integer timeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOnline;
    private String offlineLocation;
    private Integer targetScore;
    private Integer minimum;
    private Integer maximum;
    private Integer totalSessions;
    private Boolean isPrivate;
}
