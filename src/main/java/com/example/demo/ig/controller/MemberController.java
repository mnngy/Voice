package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String registerPost(Member member) {
        memberService.memberjoin(member);
        return "login";
    }

    @PostMapping("memberIdCheckAction")
    public void memberIdCheckAction(HttpServletRequest request, HttpServletResponse response) {
        memberService.memberIdDuplicateCheck(request, response);
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public void loginPost(HttpServletRequest request, HttpServletResponse response, Member member) {
        memberService.memberLogin(request, response, member);
    }
}
