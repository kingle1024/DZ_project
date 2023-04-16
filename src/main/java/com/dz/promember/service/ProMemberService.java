package com.dz.promember.service;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import com.dz.promember.vo.ProMemberVO;

public interface ProMemberService {
    boolean save(MemberVO memberVO);

    MemberVO info(String userId);
}
