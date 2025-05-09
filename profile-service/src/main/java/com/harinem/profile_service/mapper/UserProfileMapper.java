package com.harinem.profile_service.mapper;

import com.harinem.profile_service.dto.request.UserProfileCreationRequest;
import com.harinem.profile_service.dto.response.UserProfileCreationResponse;
import com.harinem.profile_service.dto.response.UserProfileResponse;
import com.harinem.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);

    UserProfileCreationResponse toUserProfileCreationResponse(UserProfile userProfile);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
