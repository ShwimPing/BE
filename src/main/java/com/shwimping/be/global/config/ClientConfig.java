package com.shwimping.be.global.config;

import com.shwimping.be.auth.util.kakao.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
public class ClientConfig {
}