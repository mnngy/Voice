package com.example.demo.ig.controller;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.repository.BoardRepository;
import com.example.demo.mg.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private final BoardRepository boardRepository;
    private final LikeService likeService;

    @Autowired
    public HomeController(BoardRepository boardRepository, LikeService likeService) {
        this.boardRepository = boardRepository;
        this.likeService = likeService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request,
                       @CookieValue(value = "cookieMemberId", required = false) Cookie cookie) {

        // "아이디 저장하기"를 했는지 확인
        if (cookie != null && !cookie.getValue().equals(null)) {
            log.info("{}님이 쿠키를 불렀습니다.", cookie.getValue());
            model.addAttribute("memberId", cookie.getValue());
        }

        HttpSession session = request.getSession();

        // 세션이 있는지 확인
        if (session.getAttribute("sessionMemberId") != null) {
            List<Board> boardList = null;
            boardList = likeService.LikeBoard();
            model.addAttribute("boardList", boardList);
            return "main";
        } else {
            return "login";
        }
    }
}
