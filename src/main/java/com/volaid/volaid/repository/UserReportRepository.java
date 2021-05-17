package com.volaid.volaid.repository;

import com.volaid.volaid.entity.UserReport;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends Neo4jRepository<UserReport, Long> {
}
