package com.sticky.sticky_back.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateResponse {
    private Integer teamId;
    private String name;
    private Integer creatorId;
    private String status;
    private Integer currentMembers;
}
