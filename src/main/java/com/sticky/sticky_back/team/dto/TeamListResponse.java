package com.sticky.sticky_back.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamListResponse {
    private List<TeamSummary> teams;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamSummary {
        private Integer teamId;
        private String name;
        private String subject;
        private Integer currentMembers;
        private Integer maximum;
        private Boolean isRecruiting;
    }
}
