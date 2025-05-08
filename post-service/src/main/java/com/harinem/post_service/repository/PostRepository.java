package com.harinem.post_service.repository;

import com.harinem.post_service.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {

    List<Post> findAllByUserId(String userId);
}
