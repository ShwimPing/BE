package com.shwimping.be.review.domain;

import com.shwimping.be.place.domain.Place;
import com.shwimping.be.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "rating", nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "review_image_url")
    private String reviewImageUrl;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "place_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Builder
    public Review(String content, LocalDate date, BigDecimal rating, String reviewImageUrl, User user, Place place) {
        this.content = content;
        this.date = date;
        this.rating = rating;
        this.reviewImageUrl = reviewImageUrl;
        this.user = user;
        this.place = place;
    }
}
