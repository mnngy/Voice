package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.domain.Member;
import com.example.demo.ig.service.BoardService;
import com.example.demo.ig.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;

    @Autowired
    public AdminController(MemberService memberService, BoardService boardService) {
        this.memberService = memberService;
        this.boardService = boardService;
    }

    @GetMapping("admin")
    public String Home() {
        return "admin/admin";
    }

    @GetMapping("admin/all")
    public String all(Model model) {
        List<Member> members = memberService.findAllMembers();
        List<Board> boards = boardService.findAllBoard();
        model.addAttribute("members", members);
        model.addAttribute("boards", boards);
        return "admin/all";
    }

    @GetMapping("admin/search")
    public String search(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (request.getParameter("category").equals("member")) {
            Member member = memberService.findMember(request.getParameter("title"));
            model.addAttribute("member", member);
            return "admin/member";
        } else {
            List<Board> boards = boardService.findBoards(Long.parseLong(request.getParameter("title")));
            model.addAttribute("boards", boards);
            return "admin/board";
        }
    }
}
