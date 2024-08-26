package com.shwimping.be.place.repository.init;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.place.domain.MongoPlace;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.repository.MongoPlaceRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class MongoPlaceInitializer implements ApplicationRunner {

    private final MongoPlaceRepository mongoPlaceRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (mongoPlaceRepository.count() > 0) {
            log.info("[MongoPlace]더미 데이터 존재");
        } else {
            importCsvToMongo("src/main/resources/data/기후동행쉼터.csv", 4, 9, 10, Category.TOGETHER);
            importCsvToMongo("src/main/resources/data/서울시 한파쉼터.csv", 2, 9, 10, Category.COLD);
            importCsvToMongo("src/main/resources/data/서울시 무더위쉼터.csv", 3, 8, 9, Category.HOT);
            importCsvToMongo("src/main/resources/data/스마트쉼터 현황.csv", 4, 6, 7, Category.SMART);
        }
    }

    private void importCsvToMongo(String filePath, int addressIndex, int longitudeIndex , int latitudeIndex,
                                  Category category) {
        try (Reader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextLine;
            // 첫 번째 줄은 헤더이므로 건너뜁니다.
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String address = nextLine[addressIndex]; // 도로명주소
                double latitude = Double.parseDouble(nextLine[latitudeIndex]); // 위도
                double longitude = Double.parseDouble(nextLine[longitudeIndex]); // 경도

                GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
                MongoPlace mongoPlace = MongoPlace.builder()
                        .address(address)
                        .location(location)
                        .category(category)
                        .build();

                mongoPlaceRepository.save(mongoPlace);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
