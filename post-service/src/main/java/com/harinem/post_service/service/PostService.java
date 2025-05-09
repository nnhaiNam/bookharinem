package com.harinem.post_service.service;

import com.harinem.post_service.dto.PageResponse;
import com.harinem.post_service.dto.request.PostRequest;
import com.harinem.post_service.dto.response.PostResponse;
import com.harinem.post_service.entity.Post;
import com.harinem.post_service.mapper.PostMapper;
import com.harinem.post_service.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PostService {

    PostRepository postRepository;
    PostMapper postMapper;
    DateTimeFormatter dateTimeFormatter;
    public PostResponse createPost(PostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId=authentication.getName();
        Post post= Post.builder()
                .userId(userId)
                .content(request.getContent())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        post=postRepository.save(post);
        return postMapper.toPostResponse(post);

    }

    public PageResponse<PostResponse> getMyPosts(int page, int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId=authentication.getName();

        Sort sort=Sort.by("createdDate").descending();

        Pageable pageable= PageRequest.of(page-1,size,sort);
        var pageData=postRepository.findAllByUserId(userId,pageable);

        var postList=pageData.getContent().stream().map(post -> {
            var postResponse=postMapper.toPostResponse(post);
            postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
            return postResponse;
        }).toList();
        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();

    }


}
