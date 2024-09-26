package com.shwimping.be.bookmark.dto.response;

import java.util.List;

public record BookMarkPlaceResponseList(
        Boolean hasNext,
        List<BookMarkPlaceResponse> bookMarkList
) {
    public static BookMarkPlaceResponseList of(Boolean hasNext, List<BookMarkPlaceResponse> bookMarkPlaceResponseList) {
        return new BookMarkPlaceResponseList(hasNext, bookMarkPlaceResponseList);
    }
}