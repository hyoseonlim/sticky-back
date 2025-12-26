package com.sticky.sticky_back.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "team_member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberEntity {

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("uuid")
    @JoinColumn(name = "uuid")
    private MemberEntity member;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberId implements Serializable {
        @Column(name = "team_id")
        private Integer teamId;

        @Column(name = "uuid")
        private Integer uuid;
    }
}
