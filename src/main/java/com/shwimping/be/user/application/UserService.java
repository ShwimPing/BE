package com.shwimping.be.user.application;

import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import com.shwimping.be.user.exception.UserNotFoundException;
import com.shwimping.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shwimping.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = request.toUser(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        userRepository.delete(user);
    }
}