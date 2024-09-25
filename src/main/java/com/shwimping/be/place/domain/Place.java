package com.shwimping.be.place.domain;

import com.shwimping.be.bookmark.domain.BookMark;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.review.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import org.locationtech.jts.geom.Point;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "place", indexes = {
        @Index(name = "idx_place_location", columnList = "location")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "region", nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "rest_info", nullable = false)
    private String restInfo;

    @Column(name = "location", nullable = false, columnDefinition = "POINT SRID 4326")
    private Point location;

    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BookMark> bookMarkList;

    @Builder
    public Place(String name, String region, Category category, String address, LocalTime openTime, LocalTime closeTime,
                 String restInfo, Point location) {
        this.name = name;
        this.region = region;
        this.category = category;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.restInfo = restInfo;
        this.location = location;
    }
}
