package com.shwimping.be.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Region {
    L1100100(Arrays.asList("서초구", "강남구", "송파구", "강동구"), "서울서북권"),
    L1100200(Arrays.asList("성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구"), "서울동남권"),
    L1100300(Arrays.asList("강서구", "양천구", "영등포구", "구로구", "금천구", "관악구", "동작구"), "서울서남권"),
    L1100400(Arrays.asList("은평구", "서대문구", "마포구"), "서울동북권"),
    ;

    private final List<String> reginList;
    private final String regionName;

    public static List<String> getRegionListByRegId(String regId) {
        for (Region region : values()) {
            if (region.name().equals(regId)) {
                return region.getReginList();
            }
        }

        // 해당하는 권역이 없는 경우 빈 리스트 반환
        return Collections.emptyList();
    }
}