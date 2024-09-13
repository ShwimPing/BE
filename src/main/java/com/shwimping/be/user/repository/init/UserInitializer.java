package com.shwimping.be.user.repository.init;


import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.global.util.NCPProperties;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Provider;
import com.shwimping.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NCPProperties ncpProperties;

    private static final String DUMMY_PROFILE_IMAGE_URL = "/profile/ic_profile.svg";

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_ADMIN = User.builder()
                    .nickname("관리자")
                    .fcmToken("fcmToken")
                    .isAlarmAllowed(true)
                    .profileImageUrl(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + DUMMY_PROFILE_IMAGE_URL)
                    .email("admin@naver.com")
                    .password(passwordEncoder.encode("adminPassword"))
                    .provider(Provider.SELF)
                    .nowLocation("nowLocation")
                    .build();

            User DUMMY_USER1 = User.builder()
                    .nickname("user1")
                    .fcmToken("fcmToken")
                    .isAlarmAllowed(true)
                    .profileImageUrl(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + DUMMY_PROFILE_IMAGE_URL)
                    .email("user1@naver.com")
                    .password(passwordEncoder.encode("user1Password"))
                    .provider(Provider.SELF)
                    .nowLocation("nowLocation")
                    .build();

            User DUMMY_USER2 = User.builder()
                    .nickname("user2")
                    .fcmToken("fcmToken")
                    .isAlarmAllowed(true)
                    .profileImageUrl(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + DUMMY_PROFILE_IMAGE_URL)
                    .email("user2@naver.com")
                    .password(passwordEncoder.encode("user2Password"))
                    .provider(Provider.SELF)
                    .nowLocation("nowLocation")
                    .build();

            userList.add(DUMMY_ADMIN);
            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);

            userRepository.saveAll(userList);
        }
    }
}
