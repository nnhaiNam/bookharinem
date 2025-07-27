package com.harinem.api_gateway.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
//modify
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectRequest {
    String token;

}
