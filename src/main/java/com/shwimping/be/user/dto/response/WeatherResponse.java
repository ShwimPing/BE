package com.shwimping.be.user.dto.response;

import lombok.Builder;

import java.util.regex.Matcher;

@Builder
public record WeatherResponse(
        String regUp,     // 상위 특보구역코드
        String regUpKo,   // 상위 특보구역명
        String regId,     // 특보구역코드
        String regKo,     // 특보구역명
        String tmFc,      // 발표시각(년월일시분, KST)
        String tmEf,      // 발효시각(년월일시분, KST)
        String wrn,       // 특보종류
        String lvl,       // 특보수준
        String cmd        // 특보명령
) {
    public static WeatherResponse of(Matcher matcher) {
        return WeatherResponse.builder()
                .regUp(matcher.group(1))
                .regUpKo(matcher.group(2))
                .regId(matcher.group(3))
                .regKo(matcher.group(4))
                .tmFc(matcher.group(5))
                .tmEf(matcher.group(6))
                .wrn(matcher.group(7))
                .lvl(matcher.group(8))
                .cmd(matcher.group(9))
                .build();
    }
}
