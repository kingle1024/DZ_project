package com.dz.promember.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProMemberVO {
    private String id;
    private String userId;
    private String pwd;
    private String name;
}
