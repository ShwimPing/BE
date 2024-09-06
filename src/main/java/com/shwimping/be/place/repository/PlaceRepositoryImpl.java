package com.shwimping.be.place.repository;

import static com.shwimping.be.place.domain.QPlace.place;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
                                Expressions.numberTemplate(Double.class,
                                        "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))",
                                        longitude, latitude).as("distance"), // 거리 계산 결과를 'distance'로 alias
                                place.category,
                                place.openTime,
                                place.closeTime
                        )
                )
                .from(place) // 여기서 'from' 사용
                .where(Expressions.numberTemplate(Double.class,
                        "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))",
                        longitude, latitude).loe(maxDistance)) // 최대 거리 조건
                .orderBy(Expressions.numberTemplate(Double.class,
                        "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))",
                        longitude, latitude).asc()) // 거리순으로 정렬
                .fetch();
    }
}
