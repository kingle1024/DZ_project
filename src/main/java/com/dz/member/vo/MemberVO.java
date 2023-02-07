package com.dz.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberVO {
    @ApiModelProperty(notes = "회원 아이디")
    private String userId;

    @ApiModelProperty(notes = "회원 비밀번호")
    private String pwd;

    @ApiModelProperty(notes = "회원 이름")
    private String name;
}
