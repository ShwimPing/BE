package com.shwimping.be.place.repository;

import static com.shwimping.be.place.domain.QPlace.place;
import static com.shwimping.be.review.domain.QReview.review;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, double maxDistance) {

        // 동적 쿼리 실행 - 결과 리스트
        return jpaQueryFactory.select(
                        Projections.constructor(SearchPlaceResponse.class,
                                place.id,
                                place.name,
                                place.address,
                                distanceTemplate(longitude, latitude).as("distance"), // 거리 계산 결과를 'distance'로 alias
                                place.category,
                                place.openTime,
                                place.closeTime,
                                review.rating.avg().coalesce(0.0) // 평균 평점을 가져오고 null일 경우 0으로 대체
                        )
                )
                .from(place)
                .leftJoin(place.reviewList, review) // Review와 조인 (가정: Place 엔티티에 reviews 필드가 있음)
                .where(distanceTemplate(longitude, latitude).loe(maxDistance)) // 최대 거리 조건
                .groupBy(place.id) // 그룹화 - review와 조인해서 그룹화
                .orderBy(distanceTemplate(longitude, latitude).asc()) // 거리순으로 정렬
                .fetch();
    }

    private NumberTemplate<Long> distanceTemplate(double longitude, double latitude) {
        return Expressions.numberTemplate(Long.class,
                "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))", longitude, latitude);
    }
}
