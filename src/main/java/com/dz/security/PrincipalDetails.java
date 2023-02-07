package com.dz.security;
import java.util.ArrayList;
import java.util.Collection;

import com.dz.member.vo.MemberVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Slf4j
public class PrincipalDetails implements UserDetails{

    private MemberVO member;

    public PrincipalDetails(MemberVO member){
        this.member = member;
    }

    public MemberVO getMember() {
        return member;
    }

    @Override
    public String getPassword() {
        return member.getPwd();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("");
        Collection<GrantedAuthority> authList = new ArrayList<>();

        member.getRoles().forEach(r -> authList.add(() -> r));
        return authList;
    }
}
