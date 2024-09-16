package com.shwimping.be.user.repository;

import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySocialId(String socialId);

    boolean existsByEmailAndProvider(String email, Provider provider);

    boolean existsByNickname(String nickname);}
