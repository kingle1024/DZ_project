package com.dz.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    @ApiModelProperty(notes = "회원 아이디")
    private String userId;

    @ApiModelProperty(notes = "회원 비밀번호")
    private String pwd;

    @ApiModelProperty(notes = "회원 이름")
    private String name;
    private String role;
    private String refresh_token;

    public List<String> getRoles(){
        if ( this.role.length() > 0 ) {
            return Arrays.asList(this.role.split(","));
        }

        return new ArrayList<>();
    }
}
