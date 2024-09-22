package com.shwimping.be.cardnews.dto.response;

import com.shwimping.be.cardnews.domain.CardNews;
import lombok.Builder;

@Builder
public record CardNewsResponse(
        Long cardNewsId,
        String title,
        String cardImageUrl
) {
    public static CardNewsResponse from(CardNews cardNews) {
        return CardNewsResponse.builder()
                .cardNewsId(cardNews.getId())
                .title(cardNews.getTitle())
                .cardImageUrl(cardNews.getCards().get(0).getCardImageUrl())
                .build();
    }
}
