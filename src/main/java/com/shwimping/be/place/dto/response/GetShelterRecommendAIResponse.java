package com.shwimping.be.place.dto.response;

import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import java.util.List;

public record GetShelterRecommendAIResponse(
        Integer distance,
        List<Category> category,
        SortType sortType,
        String keyWord
) {
}
