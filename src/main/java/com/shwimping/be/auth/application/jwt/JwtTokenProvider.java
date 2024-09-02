package com.shwimping.be.auth.application.jwt;

import com.shwimping.be.auth.application.jwt.type.JwtValidationType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Tokens generateToken(JwtUserDetails jwtUserDetails) {
        return new Tokens(createToken(jwtUserDetails, jwtProperties.accessTokenExpiration()),
                createToken(jwtUserDetails, jwtProperties.refreshTokenExpiration()));
    }

    public String createToken(JwtUserDetails jwtUserDetails, Long expireTime) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .setSubject(jwtUserDetails.userId().toString())
                .setIssuer(jwtProperties.issuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime));

        claims.put("role", jwtUserDetails.role());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    public JwtValidationType validateToken(String token) {
        try {
            if (getClaims(token).getExpiration().after(new Date())) {
                return JwtValidationType.VALID_JWT;
            } else {
                return JwtValidationType.EXPIRED_JWT_TOKEN;
            }
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        return JwtUserDetails.from(getClaims(token));
    }
}