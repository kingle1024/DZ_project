package com.dz.interceptor;

import com.dz.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("======================= interceptor =======================");

//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        System.out.println("authorizationHeader = " + authorizationHeader);
//        Claims claims = JwtTokenProvider.parseJwtToken(authorizationHeader);
//        System.out.println("claims = " + claims);

//        System.out.println("claims = " + claims);
//        System.out.println("claims.getId() = " + claims.getId());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
