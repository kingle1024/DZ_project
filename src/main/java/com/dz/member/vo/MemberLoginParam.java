package com.dz.member.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginParam {
    private String userId;
    private String pwd;
}
