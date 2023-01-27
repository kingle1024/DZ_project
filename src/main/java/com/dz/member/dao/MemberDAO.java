package com.dz.member.dao;

import com.dz.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
//@Repository
public interface MemberDAO {
    List<MemberVO> list();
}
