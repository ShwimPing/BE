package com.shwimping.be.cardnews.repository;

import com.shwimping.be.cardnews.domain.CardNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardNewsRepository extends JpaRepository<CardNews, Long> {
}
