package com.sticky.sticky_back.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateRequest {
    private String description;
    private Integer maximum;
    private Boolean isPrivate;
}
