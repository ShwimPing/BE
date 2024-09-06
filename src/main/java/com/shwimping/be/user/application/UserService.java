package com.shwimping.be.user.application;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import com.shwimping.be.user.exception.InvalidEmailException;
import com.shwimping.be.user.exception.UserNotFoundException;
import com.shwimping.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shwimping.be.user.exception.errorcode.UserErrorCode.INVALID_EMAIL;
import static com.shwimping.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(CreateUserRequest request) {
        if (!userRepository.existsByEmail(request.email())) {
            User user = request.toUser(passwordEncoder);
            userRepository.save(user);
        } else {
            throw new InvalidEmailException(INVALID_EMAIL);
        }
    }

    @Transactional
    public User findOrCreateUser(OAuthInfoResponse oAuthInfoResponse, String fcmToken) {
        return userRepository.findBySocialId(oAuthInfoResponse.getId())
                .orElseGet(() -> getUser(oAuthInfoResponse, fcmToken));
    }

    private User getUser(OAuthInfoResponse oAuthInfoResponse, String fcmToken) {
        User user = User.of(oAuthInfoResponse, fcmToken);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new UserNotFoundException(USER_NOT_FOUND);
                        });
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}