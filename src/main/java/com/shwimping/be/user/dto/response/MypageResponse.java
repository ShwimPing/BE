package com.shwimping.be.user.dto.response;

import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Provider;
import lombok.Builder;

@Builder
public record MypageResponse(
        String profileImageUrl,
        String nickname,
        Provider provider,
        Boolean isAlarmAllowed
) {
    public static MypageResponse from(User user) {
        return MypageResponse.builder()
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .isAlarmAllowed(user.getIsAlarmAllowed())
                .build();
    }
}