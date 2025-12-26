package com.sticky.sticky_back.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDocument {

    @Id
    private String id;

    @Field("report_id")
    private Integer reportId;

    @Field("uuid")
    private Integer uuid;

    @Field("review_id")
    private Integer reviewId;
}
