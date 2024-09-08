package com.shwimping.be.global.config;

import com.shwimping.be.auth.presentation.filter.JwtAccessDeniedHandler;
import com.shwimping.be.auth.presentation.filter.JwtAuthenticationEntryPoint;
import com.shwimping.be.auth.presentation.filter.JwtAuthenticationFilter;
import com.shwimping.be.auth.presentation.filter.MapAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MapAuthenticationFilter mapAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers(
                        "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/*",
                        "/webjars/**",
                        "/auth/**",
                        "/global/health-check");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증을 비활성화
                .cors(Customizer.withDefaults()) // CORS 활성화 - corsConfigurationSource 이름의 빈 사용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // JWT 사용해서 세션 사용 X
                .authorizeHttpRequests(auth -> auth // 요청에 대한 인증 설정
                        .requestMatchers("/place/**").permitAll()
                        .anyRequest().authenticated())  //이외의 요청은 전부 인증 필요
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
                            .accessDeniedHandler(jwtAccessDeniedHandler); //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mapAuthenticationFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}