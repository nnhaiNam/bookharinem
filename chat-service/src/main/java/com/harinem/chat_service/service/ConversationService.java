package com.harinem.chat_service.service;

import com.harinem.chat_service.dto.request.ConversationRequest;
import com.harinem.chat_service.dto.response.ConversationResponse;
import com.harinem.chat_service.mapper.ConversationMapper;
import com.harinem.chat_service.repository.ConversationRepository;
import com.harinem.chat_service.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService {

    ConversationRepository conversationRepository;
    ProfileClient profileClient;
    ConversationMapper conversationMapper;


    public List<ConversationResponse> myConversations(){
        return  null;
    }

    public ConversationResponse create(ConversationRequest request){
        // Fetch user information
        String userId= SecurityContextHolder.getContext().getAuthentication().getName();
        var userInfoResponse =profileClient.getProfile(userId);


        // Build Conversation Information

        return null;
    }

    private String generateParticipantHash(List<String> ids){
        StringJoiner stringJoiner=new StringJoiner("_");
        ids.forEach(stringJoiner::add);
        // SHA 256
        return stringJoiner.toString();
    }
}
