package com.dz.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberParam;
import com.dz.member.vo.MemberRoleProperty;
import com.dz.member.vo.MemberVO;
import com.dz.security.JwtProperties;
import com.dz.util.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberDAO memberDAO;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    EncryptHelper encryptHelper;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.memberDAO = memberDAO;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    public List<MemberVO> list(String search) {
        return memberDAO.list(search);
    }

    @Override
    public MemberLoginParam login(MemberLoginParam memberLoginParam) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginParam.getUserId(), memberLoginParam.getPwd());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        MemberLoginParam result = memberDAO.findById(memberLoginParam);
        return result;
    }

    @Override
    public boolean signup(MemberParam memberParam) {
        String refreshToken = JWT.create().withSubject(memberParam.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_ACCESS_TIME))
                .withClaim(JwtProperties.DB_ID_COLUMN, memberParam.getUserId())
                .withClaim("username", memberParam.getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        MemberVO memberVO = MemberVO.builder()
                .userId(memberParam.getUserId())
                .name(memberParam.getName())
                .pwd(encryptHelper.encrypt(memberParam.getPwd()))
                .role(MemberRoleProperty.ROLE_USER)
                .refresh_token(refreshToken)
                .build();
        System.out.println("memberVO = " + memberParam);
        return memberDAO.save(memberVO);
    }

    @Override
    public boolean save(MemberVO memberVO) {
        MemberVO member = memberDAO.findByIdVO(memberVO.getUserId());
        member.setName(memberVO.getName());

        return memberDAO.edit(member);
    }

    @Override
    public boolean refreshTokenUpdate(String username, String refreshToken) {
        return memberDAO.refreshTokenUpdate(username, refreshToken);
    }
}
