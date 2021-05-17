package com.volaid.volaid.repository;

import com.volaid.volaid.entity.UserFeedback;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackRepository extends Neo4jRepository<UserFeedback, Long> {
}
