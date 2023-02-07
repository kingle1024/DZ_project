package com.dz.security;

import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
// 시큐리티 설정에서 login 요청이 오면 자동으로 UserDetailService 타입으로 IoC되어 있는
// loadUserByUsername이 실행됨
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberDAO memberDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        log.info("username :: {}", username);
        MemberVO memberVO = memberDAO.findByIdVO(username);
        log.info("user :: {}", memberVO);

        if ( memberVO != null ) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            String[] roles = memberVO.getRole().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

            return new PrincipalDetails(memberVO);  // SecurityContext의 Authertication에 등록되어 인증정보를 가진다.
        }

        return null;
    }
}
