package com.shwimping.be.global.config;

import com.shwimping.be.auth.util.kakao.KakaoProperties;
import com.shwimping.be.auth.util.naver.NaverProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KakaoProperties.class, NaverProperties.class})
public class ClientConfig {
}