package com.volaid.volaid.repository;

import com.volaid.volaid.entity.UserPenalty;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPenaltyRepository  extends Neo4jRepository<UserPenalty, Long> {
}
