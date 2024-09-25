package com.shwimping.be.bookmark.application;

import com.shwimping.be.bookmark.domain.BookMark;
import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponse;
import com.shwimping.be.bookmark.repository.BookMarkRepository;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserService userService;
    private final PlaceService placeService;

    @Transactional
    public void saveBookMark(Long userId, Long placeId) {
        User user = userService.getUserById(userId);
        Place place = placeService.getPlaceById(placeId);

        // 북마크가 이미 존재하면 삭제, 존재하지 않으면 저장
        bookMarkRepository.findByUserAndPlace(user, place)
                .ifPresentOrElse(
                        bookMarkRepository::delete,
                        () -> bookMarkRepository.save(BookMark.of(user, place)));
    }

    public List<BookMarkPlaceResponse> getMyBookMark(Long userId) {
        User user = userService.getUserById(userId);
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
        return placeService.getBookMarkPlace(bookMarkList);
    }
}
