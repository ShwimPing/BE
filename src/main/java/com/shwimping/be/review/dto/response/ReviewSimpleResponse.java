package com.shwimping.be.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReviewSimpleResponse(
        Long reviewId,
        String writer,
        String content,
        BigDecimal rating,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String reviewImageUrl
) {
    @JsonProperty("reviewImageUrl")
    public String getFormattedImageUrl() {
        return reviewImageUrl == null ? "" : reviewImageUrl;
    }
}
