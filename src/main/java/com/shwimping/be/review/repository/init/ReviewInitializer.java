package com.shwimping.be.review.repository.init;

import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.place.repository.PlaceRepository;
import com.shwimping.be.review.domain.Review;
import com.shwimping.be.review.repository.ReviewRepository;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class ReviewInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[Review]더미 데이터 존재");
        } else {
            Place place1 = placeRepository.findById(506L).orElseThrow();
            Place place2 = placeRepository.findById(2611L).orElseThrow();
            Place place3 = placeRepository.findById(511L).orElseThrow();

            User user1 = userRepository.findById(1L).orElseThrow();
            User user2 = userRepository.findById(2L).orElseThrow();
            User user3 = userRepository.findById(3L).orElseThrow();

            List<Review> reviewList = new ArrayList<>();

            Review DUMMY_REVIEW1 = Review.builder()
                    .place(place1)
                    .rating(BigDecimal.valueOf(4.0))
                    .content("좋아요")
                    .user(user1)
                    .build();
            Review DUMMY_REVIEW2 = Review.builder()
                    .place(place1)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("괜찮아요")
                    .user(user2)
                    .build();

            Review DUMMY_REVIEW3 = Review.builder()
                    .place(place2)
                    .rating(BigDecimal.valueOf(4.0))
                    .content("좋아요")
                    .user(user1)
                    .build();
            Review DUMMY_REVIEW4 = Review.builder()
                    .place(place2)
                    .rating(BigDecimal.valueOf(3.0))
                    .content("괜찮아요")
                    .user(user2)
                    .build();

            Review DUMMY_REVIEW5 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user1)
                    .build();
            Review DUMMY_REVIEW6 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user2)
                    .build();
            Review DUMMY_REVIEW7 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user3)
                    .build();
            Review DUMMY_REVIEW8 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user3)
                    .build();
            Review DUMMY_REVIEW9 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user3)
                    .build();
            Review DUMMY_REVIEW10 = Review.builder()
                    .place(place3)
                    .rating(BigDecimal.valueOf(3.5))
                    .content("좋아요")
                    .user(user3)
                    .build();

            reviewList.add(DUMMY_REVIEW1);
            reviewList.add(DUMMY_REVIEW2);
            reviewList.add(DUMMY_REVIEW3);
            reviewList.add(DUMMY_REVIEW4);
            reviewList.add(DUMMY_REVIEW5);
            reviewList.add(DUMMY_REVIEW6);
            reviewList.add(DUMMY_REVIEW7);
            reviewList.add(DUMMY_REVIEW8);
            reviewList.add(DUMMY_REVIEW9);
            reviewList.add(DUMMY_REVIEW10);

            reviewRepository.saveAll(reviewList);
        }
    }
}
