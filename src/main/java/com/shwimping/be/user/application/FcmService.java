package com.shwimping.be.user.application;

import com.shwimping.be.user.dto.request.FcmSendRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcmService {
    void sendMessage(FcmSendRequest request);
    void getUserTokens(List<String> tokens, String wrn, String lvl);
}