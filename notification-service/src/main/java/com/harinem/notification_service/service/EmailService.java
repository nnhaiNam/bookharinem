package com.harinem.notification_service.service;

import com.harinem.notification_service.dto.request.EmailRequest;
import com.harinem.notification_service.dto.request.SendEmailRequest;
import com.harinem.notification_service.dto.request.Sender;
import com.harinem.notification_service.dto.response.EmailResponse;
import com.harinem.notification_service.repository.httpclient.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailService {

    EmailClient emailClient;

    String apiKey="apikey-here";


    public EmailResponse sendEmail(SendEmailRequest request){
        return emailClient.sendEmail(apiKey, EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Harinem")
                        .email("hainam000090@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build());

    }
}
