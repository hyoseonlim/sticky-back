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
public class TeamDetailResponse {
    private Integer teamId;
    private String name;
    private Object subject;
    private Object time;
    private List<Object> members;
    private Integer currentMembers;
    private Integer studyCount;
    private Integer creatorId;
}
