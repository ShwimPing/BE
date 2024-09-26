package com.shwimping.be.bookmark.repository;

import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponse;

import java.util.List;

public interface BookMarkRepositoryCustom {

    List<BookMarkPlaceResponse> getBookMarkList(Long userId, Long lastBookMarkId, Long size);

    Boolean hasNext(Long userId, Long lastBookMarkId, Long size);
}