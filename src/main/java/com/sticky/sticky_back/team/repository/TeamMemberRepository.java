package com.sticky.sticky_back.team.repository;

import com.sticky.sticky_back.team.datamodel.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, TeamMemberEntity.TeamMemberId> {

    @Query("SELECT COUNT(tm) FROM TeamMemberEntity tm WHERE tm.id.teamId = :teamId")
    long countByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT tm FROM TeamMemberEntity tm WHERE tm.id.teamId = :teamId")
    List<TeamMemberEntity> findByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT tm FROM TeamMemberEntity tm WHERE tm.id.uuid = :uuid")
    List<TeamMemberEntity> findByMemberId(@Param("uuid") Integer uuid);
}
