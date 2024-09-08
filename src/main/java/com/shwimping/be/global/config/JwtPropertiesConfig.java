package com.shwimping.be.global.config;

import com.shwimping.be.auth.application.jwt.JwtProperties;
import com.shwimping.be.global.util.NCPProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtProperties.class, NCPProperties.class})
public class JwtPropertiesConfig {
}
