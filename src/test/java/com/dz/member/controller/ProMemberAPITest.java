package com.dz.member.controller;

import com.dz.member.vo.MemberLoginParam;
import com.dz.member.vo.MemberParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProMemberAPITest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    void login() throws Exception {
        //given
        MemberLoginParam memberLoginParam = MemberLoginParam.builder()
                .userId("exist_testuser")
                .pwd("dz")
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberLoginParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(true)))
        ;
    }

    @Test
    @WithMockUser
    void signup() throws Exception {
        MemberParam memberParam = MemberParam.builder()
                .userId("notExist_testuser")
                .pwd("dz")
                .name("name")
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status", equalTo(true)))
                ;
    }

    @Test
    @WithMockUser
    void list() throws Exception {

        //when
        ResultActions resultActions = mvc.perform(get("/api/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
        ;
    }
}