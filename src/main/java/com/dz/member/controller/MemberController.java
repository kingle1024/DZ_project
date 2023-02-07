package com.dz.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MemberController {

    @GetMapping("/loginform")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model){
        log.info("list");
        model.addAttribute("list", "list");
        return "list";
    }
}
