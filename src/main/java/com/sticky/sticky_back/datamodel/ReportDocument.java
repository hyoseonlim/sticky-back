package com.sticky.sticky_back.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDocument {

    @Id
    private String id;

    @Field("team_id")
    private Integer teamId;

    @Field("report_id")
    private Integer reportId;

    @Field("week")
    private Integer week;

    @Field("is_submitted")
    @Builder.Default
    private Boolean isSubmitted = false;

    @Field("contents")
    private String contents;

    @Field("location")
    private String location;
}
