package com.sticky.sticky_back.review.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "review_test")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTestDocument {

    @Id
    private String id;

    @Field("review_id")
    private Integer reviewId;

    @Field("uuid")
    private Integer uuid;

    @Field("vocab_id")
    private Integer vocabId;
}
