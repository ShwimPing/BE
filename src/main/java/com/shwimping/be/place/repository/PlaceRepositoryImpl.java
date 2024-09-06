package com.shwimping.be.place.repository;

import static com.shwimping.be.place.domain.QPlace.place;
import static com.shwimping.be.review.domain.QReview.review;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<Category> categoryList, SortType sortType,
            long page) {

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
                                review.rating.avg().coalesce(0.0) // 평균 평점을 가져오고 null일 경우 0으로 대체
                        )
                )
                .from(place)
                .leftJoin(place.reviewList, review)
                .where(distanceTemplate(longitude, latitude).loe(maxDistance), place.category.in(categoryList))
                .groupBy(place.id)
                .orderBy(orderExpression(sortType, longitude, latitude)) // 정렬 조건 추가
                .offset(page * 10) // 페이지 번호에 따라 결과를 10개씩 가져오도록 설정
                .limit(10)
                .fetch();
    }

    private OrderSpecifier<?> orderExpression(SortType sortType, double longitude, double latitude) {
        return switch (sortType) {
            case STAR_DESC -> review.rating.avg().coalesce(0.0).desc(); // 평점이 높은 순으로 정렬
            case DISTANCE_ASC -> distanceTemplate(longitude, latitude).asc(); // 거리순으로 정렬
        };
    }

    private NumberTemplate<Long> distanceTemplate(double longitude, double latitude) {
        return Expressions.numberTemplate(Long.class,
                "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))", longitude, latitude);
    }
}
