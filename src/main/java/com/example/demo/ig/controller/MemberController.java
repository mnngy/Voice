package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/join")
    public String newMember(Member member) {
        memberService.join(member);
        return "";
    }

    @GetMapping("/member/all")
    public String showAllMembers(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "all";
    }
}
