package com.shwimping.be.cardnews.dto.response;

import java.util.List;

public record CardNewsResponseList(
        List<CardNewsResponse> cardNewsList
) {
}