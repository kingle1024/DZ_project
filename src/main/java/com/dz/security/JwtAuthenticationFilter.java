package com.dz.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final MemberDAO memberDAO;
    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
            throws AuthenticationException {

        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        log.info("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        MemberVO memberVO = null;
        try {
            memberVO = om.readValue(request.getInputStream(), MemberVO.class);
            log.info("memberVO > {}", memberVO);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        logger.debug("JwtAuthenticationFilter :: {}", memberVO);

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberVO.getUserId(), memberVO.getPwd());

        log.debug("JwtAuthenticationFilter : 토큰생성완료 > {} ", authenticationToken);

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal > {}", principal);
//        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
//        logger.debug("Authentication :: {}", principalDetailis.getUser());

        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        return authentication;
    }

    // JWT Token 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authResult ) throws IOException {
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        logger.info("successfulAuthentication");
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

//        logger.debug("UserId :: {}", principalDetailis.getUser().getUserId());
//        logger.debug("UserName :: {}", principalDetailis.getUser().getUserName());

        long systemCurrentTimeMillis = System.currentTimeMillis();
        String jwtToken = JWT.create().withSubject(principalDetailis.getMember().getUserId())
                .withExpiresAt(new Date(systemCurrentTimeMillis + JwtProperties.EXPIRATION_ACCESS_TIME))
                .withClaim(JwtProperties.DB_ID_COLUMN, principalDetailis.getMember().getUserId())
                .withClaim("username", principalDetailis.getMember().getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String refreshToken = JwtTokenProvider.makeRefreshToken(principalDetailis.getMember().getUserId(), principalDetailis.getMember().getName());

        log.info("username > {} ", principalDetailis.getMember().getUserId());
        log.info("refreshToken > {}", refreshToken);

        memberDAO.refreshTokenUpdate(principalDetailis.getMember().getUserId(), JwtProperties.TOKEN_PREFIX+refreshToken);

        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(JwtProperties.HEADER_ACCESS_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        response.addHeader("Authoization_TIME", String.valueOf(systemCurrentTimeMillis));
        response.addHeader(JwtProperties.HEADER_REFRESH_TOKEN, JwtProperties.TOKEN_PREFIX + refreshToken);
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("status", true);
        response.getWriter().print(jsonObject);
    }
}
