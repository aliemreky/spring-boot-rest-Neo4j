package com.volaid.volaid.repository;

import com.volaid.volaid.entity.AnnouncementPhoto;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementPhotoRepository extends Neo4jRepository<AnnouncementPhoto, Long> {

    @Query("MATCH(N:AnnouncementPhoto{realName:{0}}) RETURN N")
    Optional<AnnouncementPhoto> findByRealName(String realName);

}
