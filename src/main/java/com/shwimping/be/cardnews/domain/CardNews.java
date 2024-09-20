package com.shwimping.be.cardnews.domain;

import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "card_news")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CardNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CardNewsCategory cardNewsCategory;

    @OneToMany(mappedBy = "cardNews", fetch = FetchType.LAZY)
    private List<Card> cards;

    @Builder
    public CardNews(String title, String content, CardNewsCategory cardNewsCategory, List<Card> cards) {
        this.title = title;
        this.content = content;
        this.cardNewsCategory = cardNewsCategory;
        this.cards = cards;
    }
}
