package com.shwimping.be.place.domain;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "place")
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

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @Builder
    public Place(String name, String region, Category category, String address, LocalTime openTime, LocalTime closeTime,
                 String restInfo, Double latitude, Double longitude) {
        this.name = name;
        this.region = region;
        this.category = category;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.restInfo = restInfo;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
