package com.dz.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtTokenProvider {
    public static String makeAccessToken(String userId, String name, String role){
        log.info("");
        return JWT.create().withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_ACCESS_TIME))
                .withClaim(JwtProperties.DB_ID_COLUMN, userId)
                .withClaim("username", name)
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
    public static String makeRefreshToken(String userId, String name){
        log.info("");
        return JWT.create().withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_REFRESH_TIME))
                .withClaim(JwtProperties.DB_ID_COLUMN, userId)
                .withClaim("username", name)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

}
