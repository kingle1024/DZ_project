package com.dz.member.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberVO {
    private String userId;
    private String pwd;
    private String name;
}
