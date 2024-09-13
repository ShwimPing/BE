package com.shwimping.be.user.application;

import com.shwimping.be.user.dto.request.FcmSendRequest;
import org.springframework.stereotype.Service;

@Service
public interface FcmService {
    void sendMessage(FcmSendRequest request);
}