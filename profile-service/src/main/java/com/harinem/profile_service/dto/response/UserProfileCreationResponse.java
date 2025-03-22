package com.harinem.profile_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationResponse {
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
