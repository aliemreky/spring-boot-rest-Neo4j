package com.volaid.volaid.repository;

import com.volaid.volaid.entity.Announcement;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository  extends Neo4jRepository<Announcement, Long> {
}
