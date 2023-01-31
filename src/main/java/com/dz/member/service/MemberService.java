package com.dz.member.service;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;

import java.util.List;

public interface MemberService {
    List<MemberVO> list(String search);
    MemberLoginParam login(MemberLoginParam memberLoginParam);
    boolean signup(MemberVO memberVO);

    boolean save(MemberVO memberVO);
}
