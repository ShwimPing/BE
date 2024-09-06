package com.shwimping.be.place.repository.init;

import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.place.domain.MongoPlace;
import com.shwimping.be.place.repository.PlaceRepository;
import com.shwimping.be.place.repository.mongo.MongoPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class MongoPlaceInitializer implements ApplicationRunner {

    private final PlaceRepository placeRepository;
    private final MongoPlaceRepository mongoPlaceRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (mongoPlaceRepository.count() > 0) {
            log.info("[MongoPlace]더미 데이터 존재");
        } else {
            placeRepository.findAll().forEach(place -> {
                MongoPlace mongoPlace = MongoPlace.builder()
                        .category(place.getCategory())
                        .address(place.getAddress())
                        .location(new GeoJsonPoint(place.getLongitude(), place.getLatitude()))
                        .placeId(place.getId())
                        .build();

                mongoPlaceRepository.save(mongoPlace);
            });
        }
    }
}
