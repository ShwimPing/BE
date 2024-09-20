package com.shwimping.be.cardnews.application;

import com.shwimping.be.cardnews.domain.CardNews;
import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import com.shwimping.be.cardnews.dto.response.CardNewsDetailResponse;
import com.shwimping.be.cardnews.dto.response.CardNewsResponse;
import com.shwimping.be.cardnews.dto.response.CardNewsResponseList;
import com.shwimping.be.cardnews.dto.response.CardResponse;
import com.shwimping.be.cardnews.exception.CardNewsNotFoundException;
import com.shwimping.be.cardnews.repository.CardNewsRepository;
import com.shwimping.be.cardnews.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.shwimping.be.cardnews.exception.errorcode.CardNewsErrorCode.CARD_NEWS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardNewsService {

    private final CardNewsRepository cardNewsRepository;
    private final CardRepository cardRepository;

    public CardNewsResponseList getCardNewsList(CardNewsCategory cardNewsCategory) {
        List<CardNewsResponse> responseList = new ArrayList<>();

        cardNewsRepository.findAllByCardNewsCategory(cardNewsCategory)
                .forEach(cardNews -> responseList.add(CardNewsResponse.from(cardNews)));

        return new CardNewsResponseList(responseList);
    }

    public CardNewsDetailResponse getCardNewsDetail(Long cardNewsId) {
        CardNews cardNews = getCardNews(cardNewsId);

        List<CardResponse> responseList = new ArrayList<>();

        cardRepository.findAllByCardNews(cardNews)
                .forEach(card -> responseList.add(CardResponse.from(card)));

        return CardNewsDetailResponse.from(responseList);
    }

    private CardNews getCardNews(Long cardNewsId) {
        return cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new CardNewsNotFoundException(CARD_NEWS_NOT_FOUND));
    }
}