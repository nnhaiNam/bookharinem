package com.harinem.profile_service.controller;

import com.harinem.profile_service.dto.request.UserProfileCreationRequest;
import com.harinem.profile_service.dto.response.UserProfileCreationResponse;
import com.harinem.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {

    UserProfileService userProfileService;

    @PostMapping("/users")
    UserProfileCreationResponse createProfile(@RequestBody UserProfileCreationRequest request){
        return userProfileService.createProfile(request);

    }

    @GetMapping("/{profileId}")
    UserProfileCreationResponse getProfileById(@PathVariable String profileId){
        return userProfileService.getProfile(profileId);
    }


}
