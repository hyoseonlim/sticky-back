package com.sticky.sticky_back.study.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabRedis {

    private String id;
    private String english;
    private String korean;
}
