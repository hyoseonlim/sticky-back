package com.sticky.sticky_back.study.repository;

import com.sticky.sticky_back.study.datamodel.StudyRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRepository extends CrudRepository<StudyRedis, String> {

    Optional<StudyRedis> findByTeamId(Integer teamId);
}
