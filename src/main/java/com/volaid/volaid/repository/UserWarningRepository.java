package com.volaid.volaid.repository;

import com.volaid.volaid.entity.User;
import com.volaid.volaid.entity.UserWarning;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWarningRepository extends Neo4jRepository<UserWarning, Long> {

    @Query("MATCH(N:UserWarning{User:{0}}) RETURN COUNT(*)")
    Integer getCountOfUserWarning(User user);

}
