package com.shwimping.be.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.shwimping.be.user.dto.request.FcmSendRequest;
import org.springframework.stereotype.Service;

@Service
public interface FcmService {
    String sendMessage(FcmSendRequest request) throws JsonProcessingException, FirebaseMessagingException;
}