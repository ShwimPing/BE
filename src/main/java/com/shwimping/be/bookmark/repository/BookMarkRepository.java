package com.shwimping.be.bookmark.repository;

import com.shwimping.be.bookmark.domain.BookMark;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {

    Optional<BookMark> findByUserAndPlace(User user, Place place);

    Long countBookMarkByUserId(Long userId);
}
