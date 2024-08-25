package com.shwimping.be.bookmark.domain;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "book_mark")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Builder
    public BookMark(User user, Place place) {
        this.user = user;
        this.place = place;
    }
}
