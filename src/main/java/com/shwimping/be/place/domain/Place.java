package com.shwimping.be.place.domain;

import com.shwimping.be.place.domain.type.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

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

    @Column(name = "location", nullable = false)
    private Point location;

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
