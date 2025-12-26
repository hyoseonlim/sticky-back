package com.sticky.sticky_back.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subject")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "template", columnDefinition = "TEXT")
    private String template;

    @Column(name = "prompt", columnDefinition = "TEXT")
    private String prompt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamEntity team;
}
