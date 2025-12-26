package com.sticky.sticky_back.study.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorrectionRedis {

    private String id;
    private String question;
    private String answer;
    private String description;
}
