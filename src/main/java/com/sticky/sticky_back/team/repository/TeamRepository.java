package com.sticky.sticky_back.team.repository;

import com.sticky.sticky_back.team.datamodel.TeamEntity;
import com.sticky.sticky_back.team.datamodel.TeamStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    Optional<TeamEntity> findByName(String name);

    Page<TeamEntity> findByStatus(TeamStatus status, Pageable pageable);

    @Query("SELECT t FROM TeamEntity t WHERE t.status = :status " +
           "AND (:isOnline IS NULL OR t.isOnline = :isOnline)")
    Page<TeamEntity> findByStatusAndIsOnline(
        @Param("status") TeamStatus status,
        @Param("isOnline") Boolean isOnline,
        Pageable pageable
    );
}
