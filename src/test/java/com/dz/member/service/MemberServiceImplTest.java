package com.dz.member.service;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberServiceImplTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

//    @Autowired
//    MemberDAO memberDAO;


    @Test
    void login() {
        MemberLoginParam memberLoginParam = MemberLoginParam.builder()
                .userId("0127")
                .pwd("qwdaksd")
                .build();
    }

    @Test
    void signup() throws Exception {
        MemberVO memberVO = MemberVO.builder()
                .userId("0127")
                .pwd("qwdaksd")
                .build();

        //when
        ResultActions resultActions = mvc.perform(get("/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberVO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk());

//        boolean signup = memberService.signup(memberVO);



    }
}