package com.tuber.profile.service.dataaccess.repository;

import com.tuber.profile.service.dataaccess.entity.UserProfileNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileNeo4jRepository extends Neo4jRepository<UserProfileNode, String> {
    Optional<UserProfileNode> findByUserId(String userId);
}
