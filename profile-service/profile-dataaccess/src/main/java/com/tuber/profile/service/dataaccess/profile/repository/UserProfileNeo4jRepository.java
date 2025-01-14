package com.tuber.profile.service.dataaccess.profile.repository;

import com.tuber.profile.service.dataaccess.profile.entity.UserProfileNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileNeo4jRepository extends Neo4jRepository<UserProfileNode, String> {
}
