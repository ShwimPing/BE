package com.shwimping.be.cardnews.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_image_url", nullable = false)
    private String cardImageUrl;

    @JoinColumn(name = "card_news_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CardNews cardNews;

    @Builder
    public Card(String cardImageUrl, CardNews cardNews) {
        this.cardImageUrl = cardImageUrl;
        this.cardNews = cardNews;
    }
}