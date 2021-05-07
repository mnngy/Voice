package com.example.demo.ig.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model,
                       @CookieValue(value = "cookieMemberId", required = false) Cookie cookie) {

        // "아이디 저장하기"를 했는지 확인
        if (cookie != null && !cookie.getValue().equals(null)) {
            System.out.println(cookie.getValue() + " 님이 쿠키를 불렀습니다.");
            model.addAttribute("memberId", cookie.getValue());
        }
        return "login";
    }
}
