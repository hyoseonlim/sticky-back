package com.sticky.sticky_back.team.datamodel;

import com.sticky.sticky_back.team.datamodel.TeamStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "study_count")
    @Builder.Default
    private Integer studyCount = 0;

    @Column(name = "is_private")
    @Builder.Default
    private Boolean isPrivate = false;

    @Column(name = "minimum")
    @Builder.Default
    private Integer minimum = 0;

    @Column(name = "maximum")
    @Builder.Default
    private Integer maximum = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private TeamStatus status;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "is_online")
    @Builder.Default
    private Boolean isOnline = true;

    @Column(name = "location")
    private String location;
}
