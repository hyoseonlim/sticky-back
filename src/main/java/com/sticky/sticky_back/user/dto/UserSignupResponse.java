package com.sticky.sticky_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupResponse {
    private Integer uuid;
    private String nickname;
    private String name;
    private String email;
    private Integer level;
    private Double rate;
}
