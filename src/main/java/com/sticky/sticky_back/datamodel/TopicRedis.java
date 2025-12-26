package com.sticky.sticky_back.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("topic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicRedis {

    @Id
    private String id;

    @Indexed
    private Integer reportId;

    @Indexed
    private Integer topicId2;

    @Indexed
    private Integer categoryId;

    private String topic;
}
