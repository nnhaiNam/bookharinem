package com.harinem.chat_service.dto.response;

import com.harinem.chat_service.entity.ParticipantInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {
    String id;
    String type;
    String participantsHash;
    String conversationAvatar;
    String conversationName;
    List<ParticipantInfo> participants;
    Instant createDate;
    Instant modifiedDate;

}
