package com.shwimping.be.cardnews.repository.init;

import com.shwimping.be.cardnews.domain.Card;
import com.shwimping.be.cardnews.domain.CardNews;
import com.shwimping.be.cardnews.repository.CardNewsRepository;
import com.shwimping.be.cardnews.repository.CardRepository;
import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.global.util.NCPProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.ArrayList;
import java.util.List;

import static com.shwimping.be.cardnews.domain.type.Category.COLD;
import static com.shwimping.be.cardnews.domain.type.Category.HOT;

@Slf4j
@RequiredArgsConstructor
@DummyDataInit
public class CardNewsInitializer implements ApplicationRunner {

    private final CardNewsRepository cardNewsRepository;
    private final CardRepository cardRepository;
    private final NCPProperties ncpProperties;

    @Override
    public void run(ApplicationArguments args) {
        if (cardNewsRepository.count() > 0) {
            log.info("[CardNews]더미 데이터 존재");
        } else {
            List<CardNews> cardNewsList = new ArrayList<>();

            CardNews cardNews1 = CardNews.builder()
                    .title("폭염에 알아두면 좋은 무더위쉼터에 관한 모든것")
                    .category(HOT)
                    .build();

            CardNews cardNews2 = CardNews.builder()
                    .title("우리 동네 버스정류장, 일상 속 작은 쉼터")
                    .category(HOT)
                    .build();

            CardNews cardNews3 = CardNews.builder()
                    .title("폭염대비 온열질환 예방을 위한 이행 가이드 안내")
                    .category(HOT)
                    .build();

            CardNews cardNews4 = CardNews.builder()
                    .title("무더운 여름 온열질환을 조심하세요!")
                    .category(HOT)
                    .build();

            CardNews cardNews5 = CardNews.builder()
                    .title("여름철 폭염 이렇게 대비하세요!")
                    .category(HOT)
                    .build();

            CardNews cardNews6 = CardNews.builder()
                    .title("겨울철, 한파가 예보된다면 한랭질환을 조심하세요!")
                    .category(COLD)
                    .build();

            CardNews cardNews7 = CardNews.builder()
                    .title("겨울철 안전을 위협하는 한파와 폭설, 대처법은?")
                    .category(COLD)
                    .build();

            CardNews cardNews8 = CardNews.builder()
                    .title("추울 땐 뭐다? 한파 신호등!")
                    .category(COLD)
                    .build();

            CardNews cardNews9 = CardNews.builder()
                    .title("한파 국민행동요령 안내")
                    .category(COLD)
                    .build();

            cardNewsList.add(cardNews1);
            cardNewsList.add(cardNews2);
            cardNewsList.add(cardNews3);
            cardNewsList.add(cardNews4);
            cardNewsList.add(cardNews5);
            cardNewsList.add(cardNews6);
            cardNewsList.add(cardNews7);
            cardNewsList.add(cardNews8);
            cardNewsList.add(cardNews9);

            List<Card> cardList = new ArrayList<>();

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/1.jpg", cardNews1));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/2.jpg", cardNews1));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/3.jpg", cardNews1));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/4.jpg", cardNews1));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/5.jpg", cardNews1));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/swelter/6.jpg", cardNews1));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/smart/1.jpg", cardNews2));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/smart/2.jpg", cardNews2));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/smart/3.jpg", cardNews2));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/smart/4.jpg", cardNews2));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/shelter/smart/5.jpg", cardNews2));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/1.jpg", cardNews3));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/2.jpg", cardNews3));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/3.jpg", cardNews3));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/4.jpg", cardNews3));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/5.jpg", cardNews3));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/sick/6.jpg", cardNews3));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/1.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/2.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/3.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/4.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/5.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/6.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/7.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/8.jpg", cardNews4));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/solution/9.jpg", cardNews4));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%800.png", cardNews5));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%8001.png", cardNews5));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%8002.png", cardNews5));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%8003.png", cardNews5));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%8004.png", cardNews5));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/hot/countryside/%EB%82%B4%EC%A7%8005.png", cardNews5));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/1.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/2.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/3.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/4.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/5.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/6.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/7.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/8.jpg", cardNews6));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/disease/9.jpg", cardNews6));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/1.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/2.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/3.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/4.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/5.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/6.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/7.jpg", cardNews7));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/snow/8.jpg", cardNews7));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/1.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/2.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/3.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/4.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/5.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/6.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/7.jpg", cardNews8));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/blinker/8.jpg", cardNews8));

            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/solution/1.jpg", cardNews9));
            cardList.add(new Card(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/news/cold/solution/2.jpg", cardNews9));

            cardNewsRepository.saveAll(cardNewsList);
            cardRepository.saveAll(cardList);
        }
    }
}