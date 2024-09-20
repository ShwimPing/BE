package com.shwimping.be.cardnews.repository;

import com.shwimping.be.cardnews.domain.Card;
import com.shwimping.be.cardnews.domain.CardNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByCardNews(CardNews cardNews);
}
