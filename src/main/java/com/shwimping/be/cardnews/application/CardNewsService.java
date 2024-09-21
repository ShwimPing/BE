package com.shwimping.be.cardnews.application;

import com.shwimping.be.cardnews.domain.CardNews;
import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import com.shwimping.be.cardnews.dto.response.CardNewsResponse;
import com.shwimping.be.cardnews.dto.response.CardResponse;
import com.shwimping.be.cardnews.exception.CardNewsNotFoundException;
import com.shwimping.be.cardnews.repository.CardNewsRepository;
import com.shwimping.be.cardnews.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.shwimping.be.cardnews.exception.errorcode.CardNewsErrorCode.CARD_NEWS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardNewsService {

    private final CardNewsRepository cardNewsRepository;
    private final CardRepository cardRepository;

    public List<CardNewsResponse> getCardNewsList(CardNewsCategory cardNewsCategory) {
        return cardNewsRepository.findAllByCardNewsCategory(cardNewsCategory).stream()
                .map(CardNewsResponse::from)
                .toList();
    }

    public List<CardResponse> getCardNewsDetail(Long cardNewsId) {
        CardNews cardNews = getCardNews(cardNewsId);

        return cardRepository.findAllByCardNews(cardNews).stream()
                .map(CardResponse::from)
                .toList();
    }

    private CardNews getCardNews(Long cardNewsId) {
        return cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new CardNewsNotFoundException(CARD_NEWS_NOT_FOUND));
    }
}