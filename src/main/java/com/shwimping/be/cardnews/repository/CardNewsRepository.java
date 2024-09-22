package com.shwimping.be.cardnews.repository;

import com.shwimping.be.cardnews.domain.CardNews;
import com.shwimping.be.cardnews.domain.type.CardNewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardNewsRepository extends JpaRepository<CardNews, Long> {

    @Query("SELECT cn FROM CardNews cn JOIN FETCH cn.cards WHERE cn.cardNewsCategory = :cardNewsCategory")
    List<CardNews> findAllByCardNewsCategory(CardNewsCategory cardNewsCategory);
}
