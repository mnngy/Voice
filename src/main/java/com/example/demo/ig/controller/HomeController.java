package com.example.demo.ig.controller;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final LikeService likeService;

    @GetMapping("/")
    public String home(Model model,
                       HttpServletRequest request,
                       @CookieValue(value = "cookieMemberId", required = false) Cookie cookie) {

        // "아이디 저장하기"를 했는지 확인
        if (cookie != null && !cookie.getValue().equals(null)) {
            log.info("{}님이 쿠키를 불렀습니다.", cookie.getValue());
            model.addAttribute("memberId", cookie.getValue());
        }

        HttpSession session = request.getSession(false);

        // 세션이 있는지 확인
        if (session != null && session.getAttribute("sessionMemberId") != null) {
            List<Board> boardList= likeService.LikeBoard(); // ?
            model.addAttribute("boardList", boardList);
            return "redirect:/main";
        } else {
            return "login";
        }
    }
}
