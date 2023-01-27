package com.dz.member.service;

import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberDAO memberDAO;

    @Override
    public List<MemberVO> list() {

        return memberDAO.list();
    }
}
