package com.shwimping.be.user.application;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.global.application.NCPStorageService;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Provider;
import com.shwimping.be.user.domain.type.Region;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import com.shwimping.be.user.dto.request.SaveProfileRequest;
import com.shwimping.be.user.dto.request.UpdateProfileRequest;
import com.shwimping.be.user.dto.response.MypageResponse;
import com.shwimping.be.user.dto.response.WeatherResponse;
import com.shwimping.be.user.exception.InvalidEmailException;
import com.shwimping.be.user.exception.UserNotFoundException;
import com.shwimping.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.shwimping.be.user.exception.errorcode.UserErrorCode.INVALID_EMAIL;
import static com.shwimping.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @Value("${cloud.aws.cdn.domain}")
    private String cdnDomain;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NCPStorageService ncpStorageService;

    @Transactional
    public void createUser(CreateUserRequest request) {
        if (!userRepository.existsByEmailAndProvider(request.email(), Provider.SELF)) {
            User user = request.toUser(passwordEncoder, cdnDomain);
            userRepository.save(user);
        } else {
            throw new InvalidEmailException(INVALID_EMAIL);
        }
    }

    @Transactional
    public User findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findBySocialId(oAuthInfoResponse.getId())
                .orElseGet(() -> saveUser(oAuthInfoResponse));
    }

    private User saveUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.save(User.of(oAuthInfoResponse, cdnDomain));
    }

    // 프로필 등록
    @Transactional
    public void saveProfile(Long userId, SaveProfileRequest request, MultipartFile file) {
        User user = getUserById(userId);
        String profileImageUrl = uploadProfileImage(file);
        user.saveProfile(request, profileImageUrl);
    }

    // 프로필 수정
    @Transactional
    public void updateProfile(Long userId, UpdateProfileRequest request, MultipartFile file) {
        User user = getUserById(userId);
        String profileImageUrl = uploadProfileImage(file);
        user.updateProfile(request, profileImageUrl);
    }

    // 프로필 이미지가 없을 경우 빈 문자열 반환, 있을 경우 S3에 업로드
    private String uploadProfileImage(MultipartFile file) {
        return ObjectUtils.isEmpty(file) ? "" : ncpStorageService.uploadFile(file, "profile");
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new UserNotFoundException(USER_NOT_FOUND);
                        });
    }

    // 사용자의 위치 정보를 업데이트
    @Transactional
    public void updateUserLocation(Long userId, String location) {
        if (userId > 0 && StringUtils.hasText(location)) {
            userRepository.findById(userId)
                    .ifPresentOrElse(
                            user -> user.updateLocation(location),
                            () -> {
                                throw new UserNotFoundException(USER_NOT_FOUND);
                            });
        }
    }

    public List<String> getUsersByLocation(List<WeatherResponse> responses) {
        List<String> userList = new ArrayList<>();

        // 모든 유저를 한 번만 조회
        List<User> allUsers = userRepository.findAll();

        for (WeatherResponse response : responses) {
            // 현재 응답의 regId에 해당하는 권역을 찾기
            String responseRegId = response.regId(); // regId 값에서 공백 제거

            // 각 유저에 대해 NowLocation이 해당 권역에 속하는지 확인 및 알람 허용 여부 확인
            for (User user : allUsers) {
                // 유저의 현재 위치와 권역의 구 리스트를 비교
                List<String> regionList = Region.getRegionListByRegId(responseRegId);

                if (regionList.contains(user.getNowLocation()) && Boolean.TRUE.equals(user.getIsAlarmAllowed())) {
                    userList.add(user.getFcmToken());
                }
            }
        }

        return userList;
    }

    // 마이페이지 유저 정보 조회
    public MypageResponse getMypage(Long userId) {
        User user = getUserById(userId);
        return MypageResponse.from(user);
    }

    // 푸시 알림 설정
    @Transactional
    public void updateAlarm(Long userId) {
        User user = getUserById(userId);
        user.updateAlarm();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public boolean validateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}