package com.sticky.sticky_back.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topic_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "description")
    private String description;
}
