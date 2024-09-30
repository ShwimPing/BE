package com.shwimping.be.place.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.PlaceDetailResponse;
import com.shwimping.be.place.dto.response.PlaceDetailWithReviews;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.shwimping.be.bookmark.domain.QBookMark.bookMark;
import static com.shwimping.be.place.domain.QPlace.place;
import static com.shwimping.be.review.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<Category> categoryList, SortType sortType,
            String keyword, long page, long size) {

        // 동적 쿼리 실행 - 결과 리스트
        return jpaQueryFactory.select(
                        Projections.constructor(SearchPlaceResponse.class,
                                place.id,
                                place.name,
                                place.address,
                                distanceTemplate(longitude, latitude).as("distance"),
                                place.category,
                                place.openTime,
                                place.closeTime,
                                review.rating.avg().coalesce(0.0), // 평균 평점을 가져오고 null일 경우 0으로 대체
                                review.id.count().coalesce(0L) // 리뷰 수
                        )
                )
                .from(place)
                .leftJoin(place.reviewList, review)
                .where(rangeTemplate(longitude, latitude, maxDistance),
                        place.category.in(categoryList),
                        keywordSearchExpression(keyword))
                .groupBy(place.id)
                .orderBy(orderExpression(sortType, longitude, latitude), reviewCntOrder()) // 정렬 조건 추가
                .offset(page * size) // 페이지 번호에 따라 결과를 가져오도록 설정
                .limit(size + 1)
                .fetch();
    }

    private OrderSpecifier<?> orderExpression(SortType sortType, double longitude, double latitude) {
        return switch (sortType) {
            case STAR_DESC -> review.rating.avg().coalesce(0.0).desc();
            case DISTANCE_ASC -> distanceTemplate(longitude, latitude).asc(); // 거리순으로 정렬
        };
    }

    // 리뷰 수로 정렬 - 다른 정렬 조건으로 정렬한 후에
    private OrderSpecifier<?> reviewCntOrder() {
        return review.id.count().coalesce(0L).desc();
    }

    private NumberTemplate<Long> distanceTemplate(double longitude, double latitude) {
        return Expressions.numberTemplate(Long.class,
                "ST_Distance_Sphere({0}, location)",
                createPoint(latitude, longitude));
    }

    private BooleanTemplate rangeTemplate(double longitude, double latitude, int maxDistance) {
        return Expressions.booleanTemplate(
                "ST_CONTAINS(ST_BUFFER({0}, {1}), location)",
                createPoint(latitude, longitude), maxDistance);
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326); // SRID를 4326으로 설정
        return point;
    }

    private BooleanExpression keywordSearchExpression(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return place.name.contains(keyword).or(place.address.contains(keyword));
        } else {
            return null;
        }
    }

    @Override
    public PlaceDetailWithReviews findPlaceDetail(Long placeId, Long userId, Long size) {
        // 리뷰를 최신 5개만 가져오기 위한 서브쿼리
        List<ReviewSimpleResponse> recentReviews = jpaQueryFactory
                .select(Projections.constructor(ReviewSimpleResponse.class,
                        review.id,
                        review.user.nickname,
                        review.content,
                        review.rating,
                        review.date,
                        review.reviewImageUrl
                ))
                .from(review)
                .leftJoin(review.user)
                .where(review.place.id.eq(placeId))
                .orderBy(review.date.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = recentReviews.size() == size + 1;

        // PlaceDetailResponse를 생성하기 위한 쿼리
        PlaceDetailResponse placeDetailResponse = jpaQueryFactory.select(
                        Projections.constructor(PlaceDetailResponse.class,
                                place.id,
                                place.name,
                                place.address,
                                place.openTime,
                                place.closeTime,
                                place.category,
                                place.restInfo,
                                review.rating.avg().coalesce(0.0), // 평균 평점
                                bookMark.id.isNotNull() // userId가 존재하는지 확인
                        )
                )
                .from(place)
                .leftJoin(review).on(review.place.id.eq(place.id))
                .leftJoin(bookMark).on(bookMark.place.id.eq(place.id).and(bookMark.user.id.eq(userId)))
                .where(place.id.eq(placeId))
                .fetchOne();

        return new PlaceDetailWithReviews(hasNext, placeDetailResponse, recentReviews);
    }
}
