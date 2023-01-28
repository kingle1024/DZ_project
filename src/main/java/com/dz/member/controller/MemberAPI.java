package com.dz.member.controller;

import com.dz.member.service.MemberService;
import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberAPI {
    @Autowired
    MemberService memberService;

    @GetMapping("/list")
//    public String login(@RequestBody MemberParam memberParam){
    public String list(){
        List<MemberVO> list = memberService.list();

        return list.toString();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MemberLoginParam memberLoginParam){
        MemberLoginParam login = memberService.login(memberLoginParam);
        Map<String, Object> resultMap = new HashMap<>();
        if(login != null) resultMap.put("status", true);
        else resultMap.put("status", false);

        return resultMap;
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody MemberVO memberVO){
        boolean signup = memberService.signup(memberVO);
        Map<String, Object> resultMap = new HashMap<>();

        if(signup){
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
        }
        return resultMap;
    }
}
