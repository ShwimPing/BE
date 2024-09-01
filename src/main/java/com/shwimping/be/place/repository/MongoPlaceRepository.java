package com.shwimping.be.place.repository;

import com.shwimping.be.place.domain.MongoPlace;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPlaceRepository extends MongoRepository<MongoPlace, String> {

    @Query("{ location: { $near: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } } }")
    List<MongoPlace> findByLocationNear(double longitude, double latitude, double maxDistance);
}
