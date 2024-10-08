package com.shwimping.be.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.shwimping.be.user.dto.request.FcmMessageRequest;
import com.shwimping.be.user.dto.request.FcmSendRequest;
import com.shwimping.be.user.util.FcmApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    @Value("${fcm.project.name}")
    private String projectName;

    @Value("${fcm.config.path}")
    private String firebaseConfigPath;

    private final FcmApiClient fcmApiClient;

    private static final String FCM_URL = "https://www.googleapis.com/auth/firebase.messaging";

    public void getUserTokens(List<String> tokens, String wrn, String lvl) {
        for (String token : tokens) {
            FcmSendRequest request = FcmSendRequest.of(token, wrn, lvl);
            sendMessage(request);
        }
    }

    public void sendMessage(FcmSendRequest request){
        String message;

        try {
            message = makeMessage(request);
            fcmApiClient.sendMessage(projectName, "Bearer " + getAccessToken(), message);
        } catch (IOException e) {
            log.info("[-] FCM 전송 오류 :: {}", e.getMessage());
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(FCM_URL));

        googleCredentials.refreshIfExpired();
        log.info("[+] FCM Access Token :: " + googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(FcmSendRequest request) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FcmMessageRequest fcmMessageRequest = FcmMessageRequest.from(request);

        return om.writeValueAsString(fcmMessageRequest);
    }
}