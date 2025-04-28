package com.harinem.profile_service.service;

import com.harinem.profile_service.dto.request.UserProfileCreationRequest;
import com.harinem.profile_service.dto.response.UserProfileCreationResponse;
import com.harinem.profile_service.entity.UserProfile;
import com.harinem.profile_service.mapper.UserProfileMapper;
import com.harinem.profile_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileCreationResponse createProfile(UserProfileCreationRequest request){
        UserProfile userProfile=userProfileMapper.toUserProfile(request);
        userProfile=userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileCreationResponse(userProfile);
    }

    public UserProfileCreationResponse getProfile(String id){
        UserProfile userProfile=userProfileRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Profile not found!")
        );

        return userProfileMapper.toUserProfileCreationResponse(userProfile);



    }

    public List<UserProfileCreationResponse> getAll(){
        return userProfileRepository.findAll().stream().map(userProfileMapper::toUserProfileCreationResponse).toList();
    }


}
