package com.shwimping.be.bookmark.application;

import com.shwimping.be.bookmark.domain.BookMark;
import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponse;
import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponseList;
import com.shwimping.be.bookmark.repository.BookMarkRepository;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.domain.User;
import java.util.concurrent.atomic.AtomicBoolean;
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
    public String saveBookMark(Long userId, Long placeId) {
        User user = userService.getUserById(userId);
        Place place = placeService.getPlaceById(placeId);

        AtomicBoolean isCreated = new AtomicBoolean(false);

        // 북마크가 이미 존재하면 삭제, 존재하지 않으면 저장
        bookMarkRepository.findByUserAndPlace(user, place)
                .ifPresentOrElse(
                        bookmark -> {
                            bookMarkRepository.delete(bookmark);
                            isCreated.set(false);
                        },
                        () -> {
                            bookMarkRepository.save(BookMark.of(user, place));
                            isCreated.set(true);
                        });

        if (isCreated.get()) {
            return "북마크 저장 성공";
        } else {
            return "북마크 삭제 성공";
        }
    }

    public BookMarkPlaceResponseList getMyBookMark(Long userId, Long lastBookMarkId, Long size) {
        if (lastBookMarkId == 0) {
            lastBookMarkId = bookMarkRepository.countBookMarkByUserId(userId);
        }

        List<BookMarkPlaceResponse> bookMarkList = bookMarkRepository.getBookMarkList(userId, lastBookMarkId, size);

        Boolean hasNext = bookMarkRepository.hasNext(userId, lastBookMarkId, size);

        return BookMarkPlaceResponseList.of(hasNext, bookMarkList);
    }
}
