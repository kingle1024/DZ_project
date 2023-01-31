package com.dz.util;

import com.dz.member.vo.MemberLoginParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class JwtTokenProvider {
    Date now = new Date();
    private static final String secretKey = "secret";

    public String makeJwtToken(MemberLoginParam memberLoginParam){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", memberLoginParam.getUserId())
                .claim("pwd", memberLoginParam.getPwd())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static Claims parseJwtToken(String authorizationHeader){
        validationAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private static String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    private static void validationAuthorizationHeader(String header) {
        if(header == null || !header.startsWith("Bearer ")){
            throw new IllegalArgumentException();
        }
    }



}
