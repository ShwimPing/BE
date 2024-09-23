package com.shwimping.be.user.domain;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.type.Provider;
import com.shwimping.be.user.dto.request.SaveProfileRequest;
import com.shwimping.be.user.dto.request.UpdateProfileRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Column(name = "is_alarm_allowed", nullable = false)
    private Boolean isAlarmAllowed;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "now_location", nullable = false)
    private String nowLocation;

    @Builder
    public User(String nickname, String fcmToken, Boolean isAlarmAllowed, String profileImageUrl, String email,
                String password, Provider provider, String socialId, String nowLocation) {
        this.nickname = nickname;
        this.fcmToken = fcmToken;
        this.isAlarmAllowed = isAlarmAllowed;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.socialId = socialId;
        this.nowLocation = nowLocation;
    }

    public static User of(OAuthInfoResponse oAuthInfoResponse, String cdnDomain) {
        return User.builder()
                .fcmToken("temporal")
                .email(oAuthInfoResponse.getEmail())
                .provider(oAuthInfoResponse.getOAuthProvider())
                .nickname(oAuthInfoResponse.getNickname())
                .socialId(oAuthInfoResponse.getId())
                .isAlarmAllowed(true)
                .profileImageUrl(cdnDomain + "/profile/ic_profile.svg")
                .nowLocation("temporal")
                .build();
    }

    public void updateLocation(String location) {
        this.nowLocation = location;
    }

    public void saveProfile(SaveProfileRequest request, String profileImageUrl) {
        this.fcmToken = request.fcmToken();
        this.nickname = request.nickname();

        if (!profileImageUrl.isEmpty()) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public void updateProfile(UpdateProfileRequest request, String profileImageUrl) {
        this.nickname = request.nickname();

        if (!profileImageUrl.isEmpty()) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public void updateAlarm() {
        this.isAlarmAllowed = !this.isAlarmAllowed;
    }
}