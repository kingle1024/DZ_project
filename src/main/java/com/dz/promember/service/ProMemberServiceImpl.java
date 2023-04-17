package com.dz.promember.service;

import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProMemberServiceImpl implements ProMemberService {
    @Autowired
    MemberDAO memberDAO;

    @Override
    public boolean save(MemberVO memberVO) {
        return memberDAO.save(memberVO);
    }

    public MemberVO info(String userId) {
        System.out.println("userId = " + userId);
        return memberDAO.findById(userId);
    }
}
