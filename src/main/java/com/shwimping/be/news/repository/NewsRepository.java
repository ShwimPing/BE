package com.shwimping.be.news.repository;

import com.shwimping.be.news.domain.News;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaAttributeConverter<News, Long> {
}
