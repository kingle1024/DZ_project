package com.dz.member.controller;

import com.dz.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping("/login")
    public String login(){



        System.out.println("memberService = " + memberService.list());
        return memberService.list().toString();
    }
}
