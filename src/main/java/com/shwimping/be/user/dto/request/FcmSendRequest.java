package com.shwimping.be.user.dto.request;

import lombok.Builder;

@Builder
public record FcmSendRequest(
        String fcmToken,
        String title,
        String body
) {
    public static FcmSendRequest of(String fcmToken, String wrn, String lvl) {
        return FcmSendRequest.builder()
                .fcmToken(fcmToken)
                .title(wrn + lvl + " 발령!")
                .body("현재 지역에 " + wrn + lvl + "가 발령되었습니다.\r\n" +
                        "실내에서 휴식을 취하시고 \r\n" +
                        "건강에 유의하시기 바랍니다!")
                .build();
    }
}