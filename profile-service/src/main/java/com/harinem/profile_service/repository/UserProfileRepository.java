package com.harinem.profile_service.repository;

import com.harinem.profile_service.entity.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile,String> {

    Optional<UserProfile> findByUserId(String userId);


}
