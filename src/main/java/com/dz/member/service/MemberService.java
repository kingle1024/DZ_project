package com.dz.member.service;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;

import java.util.List;

public interface MemberService {
    List<MemberVO> list();
    MemberLoginParam login(MemberLoginParam memberLoginParam);
    boolean signup(MemberVO memberVO);
}
