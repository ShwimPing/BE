package com.shwimping.be.place.repository.init;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.shwimping.be.global.util.DummyDataInit;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.repository.PlaceRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class PlaceInitializer implements ApplicationRunner {

    private final PlaceRepository placeRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (placeRepository.count() > 0) {
            log.info("[Place]더미 데이터 존재");
        } else {
            importCsvToPlace("data/기후동행쉼터.csv", 4, 9, 10, Category.TOGETHER, 2, 3, 7, 8);
            importCsvToPlace("data/도서관 쉼터.csv", 6, 7, 8, Category.LIBRARY, 2, 1, 3, 4);
            importCsvToPlace("data/서울시 무더위쉼터.csv", 3, 7, 8, Category.HOT, 2, 11, Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
            importCsvToPlace("data/서울시 한파쉼터.csv", 2, 9, 10, Category.COLD, 1, 14, 7, 8);
            importCsvToPlace("data/스마트쉼터 현황.csv", 4, 6, 7, Category.SMART, 2, 1, Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }

    private void importCsvToPlace(String filePath, int addressIdx, int longitudeIdx, int latitudeIdx, Category category,
                                  int nameIdx, int regionIdx, int openTimeIdx, int closeTimeIdx) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        ) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextLine;
            // 첫 번째 줄은 헤더이므로 건너뜁니다.
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String address = nextLine[addressIdx]; // 도로명주소
                double latitude = Double.parseDouble(nextLine[latitudeIdx]); // 위도
                double longitude = Double.parseDouble(nextLine[longitudeIdx]); // 경도
                String name = nextLine[nameIdx];
                String region = nextLine[regionIdx];
                LocalTime openTime =
                        nextLine.length > openTimeIdx ? LocalTime.parse(nextLine[openTimeIdx]) : LocalTime.of(0, 0);

                String closeTimeString = nextLine.length > closeTimeIdx ? nextLine[closeTimeIdx] : "00:00:00";
                if ("24:00:00".equals(closeTimeString) || "00:00:00".equals(closeTimeString)) {
                    closeTimeString = "23:59:00";
                }
                LocalTime closeTime = LocalTime.parse(closeTimeString);

                String restInfo = "";
                if (category.equals(Category.LIBRARY)) {
                    restInfo = nextLine[9];
                }

                Place place = Place.builder()
                        .name(name)
                        .region(region)
                        .openTime(openTime)
                        .closeTime(closeTime)
                        .address(address)
                        .location(createPoint(latitude, longitude))
                        .category(category)
                        .restInfo(restInfo)
                        .build();

                placeRepository.save(place);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326); // SRID를 4326으로 설정
        return point;
    }
}
