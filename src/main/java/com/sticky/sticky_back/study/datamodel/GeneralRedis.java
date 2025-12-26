package com.sticky.sticky_back.study.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralRedis {

    private String id;

    @Builder.Default
    private List<CorrectionRedis> corrections = new ArrayList<>();

    @Builder.Default
    private List<VocabRedis> vocabs = new ArrayList<>();

    @Builder.Default
    private List<ExpressionRedis> expressions = new ArrayList<>();
}
