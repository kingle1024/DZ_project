package com.dz.promember.dao;

import com.dz.promember.vo.ProMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProMemberDAO {
    boolean save(ProMemberVO proMemberVO);
    ProMemberVO findById(String userId);
}
