package com.dz.security;

public interface JwtProperties {
    String SECRET = "1234"; // 우리 서버만 알고 있는 비밀값
    long EXPIRATION_ACCESS_TIME = 1; // 1시간
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_ACCESS_STRING = "Authorization";
    String DB_ID_COLUMN = "userId";
    String HEADER_REFRESH_TOKEN = "RefreshToken";
    long EXPIRATION_REFRESH_TIME = 864000000; // 3600 * 24 * 10 -> 10일
}
