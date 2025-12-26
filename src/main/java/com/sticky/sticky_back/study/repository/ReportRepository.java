package com.sticky.sticky_back.study.repository;

import com.sticky.sticky_back.study.datamodel.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<ReportDocument, String> {

    List<ReportDocument> findByTeamIdOrderByWeekAsc(Integer teamId);

    Optional<ReportDocument> findByTeamIdAndWeek(Integer teamId, Integer week);

    long countByTeamId(Integer teamId);
}
