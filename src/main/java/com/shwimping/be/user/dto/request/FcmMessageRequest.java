package com.shwimping.be.user.dto.request;

import lombok.Builder;

@Builder
public record FcmMessageRequest(
        boolean validateOnly,
        Message message
) {
    @Builder
    public record Message(
            String token,
            Notification notification
    ) {
        @Builder
        public record Notification(
                String title,
                String body
        ) {
        }
    }

    public static FcmMessageRequest from(FcmSendRequest request) {
        return FcmMessageRequest.builder()
                .message(FcmMessageRequest.Message.builder()
                        .token(request.fcmToken())
                        .notification(Message.Notification.builder()
                                .title(request.title())
                                .body(request.body())
                                .build()
                        ).build())
                .validateOnly(false)
                .build();
    }
}