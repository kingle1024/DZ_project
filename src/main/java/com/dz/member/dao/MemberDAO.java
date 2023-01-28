package com.dz.member.dao;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MemberDAO {
    List<MemberVO> list();
    MemberLoginParam findById(MemberLoginParam memberLoginParam);

    boolean save(MemberVO memberVO);
}
