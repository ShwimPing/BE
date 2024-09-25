package com.shwimping.be.bookmark.dto.response;

import java.util.List;

public record BookMarkPlaceResponseList(
        List<BookMarkPlaceResponse> bookMarkList
) {
    public static BookMarkPlaceResponseList from(List<BookMarkPlaceResponse> bookMarkPlaceResponseList) {
        return new BookMarkPlaceResponseList(bookMarkPlaceResponseList);
    }
}