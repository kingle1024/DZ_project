package com.dz.promember.controller;

import com.auth0.jwt.JWT;
import com.dz.member.vo.MemberVO;
import com.dz.promember.service.ProMemberService;
import com.dz.security.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class ProMemberAPI {
    @Autowired
    ProMemberService proMemberService;

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody MemberVO memberVO){

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", proMemberService.save(memberVO));

        return resultMap;
    }

    @PostMapping("/info")
    public Map<String, Object> info(@RequestBody Map<String, Object> map){

        Map<String, Object> resultMap = new HashMap<>();

        MemberVO info = proMemberService.info((String) map.get("userId"));
        System.out.println("info = " + info);
        resultMap.put("status", true);
        resultMap.put("info", info);
        return resultMap;
    }
}
