package com.shwimping.be.user.application;

import com.shwimping.be.user.dto.response.WeatherResponse;
import com.shwimping.be.user.util.KmaApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${kma.weather.key}")
    String key;

    private final KmaApiClient kmaApiClient;
    private final UserService userService;
    private final FcmService fcmService;

    public List<WeatherResponse> getWeatherWarning() {
        // Open API로 요청을 보내기 위해 필요한 파라미터 설정
        String fe = "f";

        // 현재 날짜 가져오기
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        // 시간을 오후 23시 59분으로 설정하기
        LocalDateTime dateTimeWithSpecificTime = now.withHour(23).withMinute(59).withSecond(0).withNano(0);
        // 원하는 형식으로 포맷팅
        String tm = dateTimeWithSpecificTime.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm"));

        String help = "1";

        // Feign 클라이언트를 사용하여 API 요청 보내기
        String response = kmaApiClient.getWeatherWarning(fe, tm, help, key);
        return extractWeatherData(response);
    }

    private List<WeatherResponse> extractWeatherData(String response) {
        List<WeatherResponse> weatherResponses = new ArrayList<>();

        // 헤더 부분을 넘기기 위한 문자열
        String headerEndMarker = "# REG_UP  REG_UP_KO-------------------------------  REG_ID    REG_KO----------------------------------  TM_FC         TM_EF         WRN   LVL   CMD   ED_TM";

        // 헤더 부분의 끝을 찾기
        int startIndex = response.indexOf(headerEndMarker);

        // 헤더 부분을 찾지 못한 경우 예외 처리
        if (startIndex == -1) {
            log.error("헤더 부분을 찾을 수 없습니다.");
            return weatherResponses;
        }

        // 헤더 부분을 건너뛰기 위해 시작 인덱스를 헤더의 끝 지점으로 설정
        startIndex += headerEndMarker.length();

        // 데이터 부분만 추출 (앞뒤 공백을 제거하기 위해 trim())
        String dataSection = response.substring(startIndex).trim();

        // 정규 표현식 패턴 정의
        String regex = "(\\w+),\\s*([^,]+),\\s*(\\w+),\\s*([^,]+),\\s*(\\d+),\\s*(\\d+),\\s*([^,]+),\\s*([^,]*),\\s*([^,]*),\\s*([^=]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dataSection);

        String wrn = "";
        String lvl = "";

        // '서울특별시'에 '오늘' 특보가 났는지 확인하기 위한 값
        String targetRegUp = "L1100000";
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("[+] WeatherService now = {}", now);

        while (matcher.find()) {
            String tmFc = matcher.group(5).substring(0, 8); // tmFc 값

            // regUp 값이 원하는 값과 일치하는 경우만 추가
            if (matcher.group(1).equals(targetRegUp) && tmFc.equals(now)) {
                wrn = matcher.group(7); // wrn 값
                lvl = matcher.group(8); // lvl 값
                weatherResponses.add(WeatherResponse.of(matcher));
            }
        }

        List<String> userList = userService.getUsersByLocation(weatherResponses);
        fcmService.getUserTokens(userList, wrn, lvl);

        return weatherResponses;
    }
}