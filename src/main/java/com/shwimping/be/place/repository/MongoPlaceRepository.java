package com.shwimping.be.place.repository;

import com.shwimping.be.place.domain.mongodb.MongoPlace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPlaceRepository extends MongoRepository<MongoPlace, String> {
}