package com.sticky.sticky_back.study.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expression")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vocab_id")
    private Integer vocabId;

    @Column(name = "english", length = 30)
    private String english;

    @Column(name = "korean", length = 30)
    private String korean;

    @Column(name = "example", length = 100)
    private String example;

    @Column(name = "count")
    @Builder.Default
    private Integer count = 0;
}
