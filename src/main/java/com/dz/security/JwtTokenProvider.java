package com.dz.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtTokenProvider {
    public static String makeRefreshToken(String userId, String name){
        return JWT.create().withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_REFRESH_TIME))
                .withClaim(JwtProperties.DB_ID_COLUMN, userId)
                .withClaim("username", name)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
