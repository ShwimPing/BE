package com.shwimping.be.place.domain;

import com.shwimping.be.place.domain.type.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "place")
public class MongoPlace {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Field(name = "category")
    private Category category;

    @Field(name = "address")
    private String address;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @Field(name = "location")
    private GeoJsonPoint location;

    @Indexed(unique = true)
    @Field(name = "place_id")
    private Long placeId;

    @Builder
    public MongoPlace(Category category, String address, GeoJsonPoint location, Long placeId) {
        this.category = category;
        this.address = address;
        this.location = location;
        this.placeId = placeId;
    }
}