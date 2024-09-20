package com.shwimping.be.cardnews.repository;

import com.shwimping.be.cardnews.domain.CardNews;
import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardNewsRepository extends JpaRepository<CardNews, Long> {

    @EntityGraph(attributePaths = "cards")
    List<CardNews> findAllByCardNewsCategory(CardNewsCategory cardNewsCategory);
}
