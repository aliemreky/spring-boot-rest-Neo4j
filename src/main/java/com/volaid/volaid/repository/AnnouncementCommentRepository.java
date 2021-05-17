package com.volaid.volaid.repository;

import com.volaid.volaid.entity.AnnouncementComment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementCommentRepository extends Neo4jRepository<AnnouncementComment, Long> {
}
