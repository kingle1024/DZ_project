package com.dz.member.vo;

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
    private String userId;
    private String pwd;
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
