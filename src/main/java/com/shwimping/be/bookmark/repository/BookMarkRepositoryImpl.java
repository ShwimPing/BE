package com.shwimping.be.bookmark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shwimping.be.bookmark.domain.QBookMark.bookMark;
import static com.shwimping.be.place.domain.QPlace.place;
import static com.shwimping.be.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookMarkPlaceResponse> getBookMarkList(Long userId, Long lastBookMarkId, Long size) {
        return queryFactory.select(
                        Projections.constructor(BookMarkPlaceResponse.class,
                                bookMark.id,              // bookmarkId
                                place.id,                 // placeId
                                place.name,               // name
                                place.address,            // address
                                place.category,           // category
                                place.openTime,           // openTime
                                place.closeTime,          // closeTime
                                review.rating.avg().coalesce(0.0), // averageRating
                                review.id.count().coalesce(0L)      // reviewCount
                        )
                )
                .from(bookMark)                    // 북마크 테이블에서 시작
                .join(bookMark.place, place)      // 북마크와 장소 조인
                .leftJoin(place.reviewList, review) // 장소와 리뷰를 왼쪽 조인
                .where(bookMark.user.id.eq(userId), // 사용자 ID 필터
                        bookMark.id.lt(lastBookMarkId)) // 마지막 북마크 ID 필터
                .groupBy(bookMark.id) // 그룹화 추가
                .orderBy(bookMark.id.desc())         // 내림차순 정렬
                .limit(size)                          // 제한
                .fetch();
    }



    @Override
    public Boolean hasNext(Long userId, Long lastBookMarkId, Long size) {
        return queryFactory.selectOne()
                .from(bookMark)
                .where(bookMark.user.id.eq(userId), bookMark.id.lt(lastBookMarkId - size))
                .fetchFirst() != null;
    }
}