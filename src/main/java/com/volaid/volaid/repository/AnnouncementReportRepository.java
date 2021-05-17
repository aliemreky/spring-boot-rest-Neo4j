package com.volaid.volaid.repository;

import com.volaid.volaid.entity.AnnouncementReport;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementReportRepository extends Neo4jRepository<AnnouncementReport, Long> {
}
