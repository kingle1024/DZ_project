package com.dz.member.service;

import com.dz.member.dao.MemberDAO;
import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import com.dz.util.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberDAO memberDAO;

    @Autowired
    EncryptHelper encryptHelper;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public List<MemberVO> list() {

        return memberDAO.list();
    }

    @Override
    public MemberLoginParam login(MemberLoginParam memberLoginParam) {
        MemberLoginParam result = memberDAO.findById(memberLoginParam);
        return result;
    }

    @Override
    public boolean signup(MemberVO memberVO) {
        memberVO.setPwd(encryptHelper.encrypt(memberVO.getPwd()));

        return memberDAO.save(memberVO);
    }
}
