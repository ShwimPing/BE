package com.shwimping.be.cardnews.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CardNewsResponseList(
        List<CardNewsResponse> cardNewsList
) {
    public static CardNewsResponseList from(List<CardNewsResponse> cardNewsList) {
        return CardNewsResponseList.builder()
                .cardNewsList(cardNewsList)
                .build();
    }
}