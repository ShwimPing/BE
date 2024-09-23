package com.shwimping.be.bookmark.repository;

import com.shwimping.be.bookmark.domain.BookMark;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Optional<BookMark> findByUserAndPlace(User user, Place place);
}
