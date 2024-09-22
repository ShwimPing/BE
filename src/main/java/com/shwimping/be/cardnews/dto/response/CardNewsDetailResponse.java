package com.shwimping.be.cardnews.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CardNewsDetailResponse(
        List<CardResponse> cardImageUrlList
) {
    public static CardNewsDetailResponse from(List<CardResponse> cardList) {
        return CardNewsDetailResponse.builder()
                .cardImageUrlList(cardList)
                .build();
    }
}