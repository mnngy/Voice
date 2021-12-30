package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String registerPost(@ModelAttribute Member member) {
        memberService.memberJoin(member);
        return "redirect:/main";
    }

    @PostMapping("memberIdCheckAction")
    @ResponseBody
    public String memberIdCheckAction(@RequestParam String memberId) {
        return memberService.memberIdDuplicateCheck(memberId);
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public void loginPost(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute Member member) {

        String memberIdRemember = request.getParameter("memberIdRemember");

        // 아이디 기억하기 버튼을 눌렀는지 확인
        memberIdCheck(response, member, memberIdRemember);

        memberService.memberLogin(request, response, member);
    }

    private void memberIdCheck(HttpServletResponse response, Member member, String memberIdRemember) {
        if (memberIdRemember != null && memberIdRemember.equals("memberIdCheck")) {
            log.info("아이디 기억하기를 선택했습니다.");
            Cookie cookie = new Cookie("cookieMemberId", member.getMemberId());
            response.addCookie(cookie);
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/";
        }
        session.invalidate();
        return "redirect:/";
    }
}
