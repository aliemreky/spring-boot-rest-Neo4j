package com.volaid.volaid.repository;

import com.volaid.volaid.entity.AnnouncementCategory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementCategoryRepository extends Neo4jRepository<AnnouncementCategory, Long> {

    @Query("MATCH(N:AnnouncementCategory{categoryName:{0}}) RETURN COUNT(*)")
    Integer countCategoryByCategoryName(String categoryName);

}
