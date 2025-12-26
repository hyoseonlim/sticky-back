package com.sticky.sticky_back.user.datamodel;

import com.sticky.sticky_back.study.datamodel.ExpressionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "member_expression")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberExpressionEntity {

    @EmbeddedId
    private MemberExpressionId id;

    @Column(name = "count")
    @Builder.Default
    private Integer count = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private ExpressionEntity expression;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("uuid")
    @JoinColumn(name = "uuid")
    private MemberEntity member;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberExpressionId implements Serializable {
        @Column(name = "review_id")
        private Integer reviewId;

        @Column(name = "uuid")
        private Integer uuid;
    }
}
