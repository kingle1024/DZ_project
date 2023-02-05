package com.dz.member.controller;

import com.dz.member.service.MemberService;
import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberParam;
import com.dz.member.vo.MemberVO;
import com.dz.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class MemberAPI {
    @Autowired
    MemberService memberService;

    @GetMapping("/list")
    public Map<String, Object> list(){
        log.info("list");
        List<MemberVO> list = memberService.list("");
//        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");
        // search, pageIndex
        PageUtil pageUtil = new PageUtil();
        PageUtil util = pageUtil.getUtil("2","", list, 20);

        System.out.println("util.getList() = " + util.getList());
        System.out.println("util.paper() = " + util.paper());
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }
    @PostMapping("/list")
    public Map<String, Object> postList(){
        log.info("list");
        List<MemberVO> list = memberService.list("");
//        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");
        // search, pageIndex
        PageUtil pageUtil = new PageUtil();
        PageUtil util = pageUtil.getUtil("2","", list, 20);

        System.out.println("util.getList() = " + util.getList());
        System.out.println("util.paper() = " + util.paper());
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MemberLoginParam memberLoginParam
                                     ){
        log.info("들어오나 ? ");
        MemberLoginParam login = memberService.login(memberLoginParam);
        Map<String, Object> resultMap = new HashMap<>();
        log.info("login > {}", login);

        if(login != null){
            resultMap.put("status", true);
//            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
//            String token = "Bearer " + jwtTokenProvider.makeJwtToken(memberLoginParam);
//            log.info("token > {}", token);
//            response.setHeader("AUTHORIZATION", token);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @GetMapping("/login")
    public Map<String, Object> testlogin(){
        MemberLoginParam memberLoginParam = MemberLoginParam.builder()
                .userId("123")
                .pwd("123")
                .build();

        MemberLoginParam login = memberService.login(memberLoginParam);
        Map<String, Object> resultMap = new HashMap<>();
        log.info("login > {}", login);

        if(login != null){
            resultMap.put("status", true);
//            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
//            String token = "Bearer " + jwtTokenProvider.makeJwtToken(memberLoginParam);
//            log.info("token > {}", token);
//            response.setHeader("AUTHORIZATION", token);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @GetMapping("/test/edit")
    public Map<String, Object> testedit(){
        System.out.println("MemberAPI.testedit");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);

        return resultMap;
    }

    @PostMapping("/test/edit")
    public Map<String, Object> posttestedit(){
        log.info("testEdit");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("data", "data");

        return resultMap;
    }

    @GetMapping("/abc/edit")
    public Map<String, Object> abcedit(){
        System.out.println("MemberAPI.abcedit");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);

        return resultMap;
    }


    @PostMapping("/member/edit")
    public Map<String, Object> edit(@RequestBody MemberVO memberVO,
            HttpServletRequest request){
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
    public Map<String, Object> signup(@RequestBody MemberParam memberParam){
        log.info("join > ");
        boolean signup = memberService.signup(memberParam);
        Map<String, Object> resultMap = new HashMap<>();

        if(signup){
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
        }
        return resultMap;
    }
}
