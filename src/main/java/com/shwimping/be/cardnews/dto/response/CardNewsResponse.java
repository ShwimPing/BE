package com.shwimping.be.cardnews.dto.response;

import com.shwimping.be.cardnews.domain.CardNews;
import lombok.Builder;

@Builder
public record CardNewsResponse(
        String title,
        String contentImageUrl
) {
    public static CardNewsResponse from(CardNews cardNews) {
        return CardNewsResponse.builder()
                .title(cardNews.getTitle())
                .contentImageUrl(cardNews.getCards().get(0).getCardImageUrl())
                .build();
    }
}
