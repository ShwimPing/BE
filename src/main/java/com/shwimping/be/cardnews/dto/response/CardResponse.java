package com.shwimping.be.cardnews.dto.response;

import com.shwimping.be.cardnews.domain.Card;
import lombok.Builder;

@Builder
public record CardResponse(
        String cardImageUrl
) {
    public static CardResponse from(Card card) {
        return CardResponse.builder()
                .cardImageUrl(card.getCardImageUrl())
                .build();
    }
}
