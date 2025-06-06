package com.harinem.post_service.repository;

import com.harinem.post_service.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {

    Page<Post> findAllByUserId(String userId, Pageable pageable);

}
