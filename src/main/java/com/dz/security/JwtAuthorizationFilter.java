package com.dz.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
    private final MemberDAO memberDAO;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberDAO memberDAO) {
        super(authenticationManager);
        this.memberDAO = memberDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        log.info("request.getRequestURI > {} ", request.getRequestURI());
        String accessToken = request.getHeader(JwtProperties.HEADER_ACCESS_STRING);
        if(accessToken == null || !accessToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            log.info("AccessToken Error > {}", accessToken);
            return;
        }

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
        String accessTokenReplace = accessToken.replace(JwtProperties.TOKEN_PREFIX, "");
        log.info("token >> {}",JWT.decode(accessTokenReplace).getSubject());

        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessTokenReplace)
            .getClaim(JwtProperties.DB_ID_COLUMN).asString();

        log.debug("username :: {}", username);

        if(username != null) {
            MemberVO user = memberDAO.findByIdVO(username);

            String refreshToken = request.getHeader(JwtProperties.HEADER_REFRESH_TOKEN);
            if(user.getRefresh_token().equals(refreshToken)){
                // freshToken 갱신
                String refreshTokenMake = JwtTokenProvider.makeRefreshToken(user.getUserId(), user.getName());
                response.setHeader(JwtProperties.HEADER_REFRESH_TOKEN, refreshTokenMake);
                memberDAO.refreshTokenUpdate(user.getUserId(), refreshTokenMake);
            }

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        chain.doFilter(request, response);
    }
}
