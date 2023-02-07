package com.dz.member.dao;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDAO {
    List<MemberVO> list(String search);
    MemberLoginParam findById(MemberLoginParam memberLoginParam);
    MemberVO findByIdVO(String userId);
    long listSize(String search);

    boolean save(MemberVO memberVO);
    boolean edit(MemberVO member);
    boolean refreshTokenUpdate(@Param("userId") String userId, @Param("refresh_token") String refresh_token);
}
