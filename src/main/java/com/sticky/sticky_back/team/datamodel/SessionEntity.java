package com.sticky.sticky_back.team.datamodel;

import com.sticky.sticky_back.team.datamodel.DayOfWeek;
import com.sticky.sticky_back.team.datamodel.TimeSlot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "session")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "session")
    @Builder.Default
    private Integer session = 1;

    @Column(name = "date", length = 50)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "day", length = 50)
    private DayOfWeek day;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_time", length = 50)
    private TimeSlot startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "end_time", length = 50)
    private TimeSlot endTime;

    @Column(name = "is_completed")
    @Builder.Default
    private Boolean isCompleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamEntity team;
}
