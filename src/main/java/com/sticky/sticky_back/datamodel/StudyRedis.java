package com.sticky.sticky_back.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("study")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRedis {

    @Id
    private String id;

    @Indexed
    private Integer reportId;

    @Indexed
    private Integer teamId;

    private String contents;
}
