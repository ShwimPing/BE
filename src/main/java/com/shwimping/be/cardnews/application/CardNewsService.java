package com.shwimping.be.cardnews.application;

import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import com.shwimping.be.cardnews.dto.response.CardNewsResponse;
import com.shwimping.be.cardnews.dto.response.CardNewsResponseList;
import com.shwimping.be.cardnews.repository.CardNewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardNewsService {

    private final CardNewsRepository cardNewsRepository;

    public CardNewsResponseList getCardNewsList(CardNewsCategory cardNewsCategory) {
        List<CardNewsResponse> responseList = new ArrayList<>();
        cardNewsRepository.findAllByCardNewsCategory(cardNewsCategory).forEach(cardNews -> responseList.add(CardNewsResponse.from(cardNews)));
        return new CardNewsResponseList(responseList);
    }
}