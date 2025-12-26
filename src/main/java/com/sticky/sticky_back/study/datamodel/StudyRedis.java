package com.sticky.sticky_back.study.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@RedisHash("study")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRedis {

    @Id
    private String id;

    @Indexed
    private Integer teamId;

    @Builder.Default
    private List<TopicRedis> topics = new ArrayList<>();

    private GeneralRedis general;
}
