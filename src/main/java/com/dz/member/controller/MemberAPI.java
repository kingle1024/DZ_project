package com.dz.member.controller;

import com.dz.member.service.MemberService;
import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import com.dz.util.JwtTokenProvider;
import com.dz.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MemberAPI {
    @Autowired
    MemberService memberService;

    @GetMapping("/list")
    public String list(){
        List<MemberVO> list = memberService.list("");
//        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");
        // search, pageIndex
        PageUtil pageUtil = new PageUtil();
        PageUtil util = pageUtil.getUtil("2","", list, 20);

        System.out.println("util.getList() = " + util.getList());
        System.out.println("util.paper() = " + util.paper());

        return list.toString();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MemberLoginParam memberLoginParam,
                                     HttpServletResponse response){

        MemberLoginParam login = memberService.login(memberLoginParam);
        Map<String, Object> resultMap = new HashMap<>();
        log.info("login > {}", login);

        if(login != null){
            resultMap.put("status", true);
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
            String token = jwtTokenProvider.makeJwtToken(memberLoginParam);
            log.info("token > {}", token);
//            response.setHeader("AUTHORIZATION", token);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @PostMapping("/edit")
    public Map<String, Object> edit(@RequestBody MemberVO memberVO,
            HttpServletRequest request, HttpServletResponse response){
        log.info("request auth > {}" , request.getHeader("AUTHORIZATION"));

//        MemberVO memberVO = MemberVO.builder()
//                .userId("exist_testuser")
//                .pwd("dz")
//                .name("aaa")
//                .build();

        boolean result = memberService.save(memberVO);


        Map<String, Object> resultMap = new HashMap<>();
        if(result) resultMap.put("status", true);
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
