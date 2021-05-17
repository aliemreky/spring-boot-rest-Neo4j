package com.volaid.volaid.repository;

import com.volaid.volaid.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH(N:User{email:{0}}) RETURN COUNT(*)")
    Integer countUserByEmail(String email);

    @Query("MATCH(N:User{username:{0}}) RETURN N")
    Optional<User> findByUsername(String username);

    @Query("MATCH(N:User{token:{0}}) RETURN N")
    Optional<User> findbyToken(String token);

    Optional<User> findByPhoneNumber(String phoneNumber);


}
