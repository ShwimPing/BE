package com.shwimping.be.user.dto.request;

public record FcmSendRequest(
        String fcmToken,
        String title,
        String body
) {
}