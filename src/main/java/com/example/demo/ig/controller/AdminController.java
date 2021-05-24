package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.domain.Member;
import com.example.demo.ig.repository.BoardRepository;
import com.example.demo.ig.service.BoardService;
import com.example.demo.ig.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        List<Member> members = memberService.findAllMember();
        List<Board> boards = boardService.findAllBoard();
        model.addAttribute("members", members);
        model.addAttribute("boards", boards);
        return "admin/all";
    }

    @GetMapping("admin/search")
    public String search(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Board> boards = null;
        Member member = null;
        response.setContentType("text/html;charset=euc-kr");

        if (request.getParameter("category").equals("member")) {
            member = memberService.findMember(request.getParameter("title"));
            model.addAttribute("member", member);
            return "admin/member";
        } else {
            try {
                boards = boardService.findBoards(Long.parseLong(request.getParameter("title")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            model.addAttribute("boards", boards);
            return "admin/board";
        }
    }

    @GetMapping("admin/edit")
    public String edit(HttpServletRequest request, Model model) {
        if (request.getParameter("memberIdx") != null) {
            Long memberIdx = Long.parseLong(request.getParameter("memberIdx"));
            Member member = memberService.findMember(memberIdx);
            model.addAttribute("member", member);
            return "admin/edit-member";
        } else {
            Long boardIdx = Long.parseLong(request.getParameter("boardIdx"));
            Board board = boardService.findBoard(boardIdx);
            model.addAttribute("board", board);
            return "admin/edit-board";
        }
    }

    @PostMapping("admin/editMember")
    public void editMember(Member member, HttpServletResponse response) {
        memberService.editMember(member, response);
    }

    @PostMapping("admin/editBoard")
    public void editBoard(Board board, HttpServletResponse response) {
        boardService.editBoard(board, response);
    }

    @GetMapping("admin/delete")
    public void String(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("memberIdx") != null) {
            Long memberIdx = Long.parseLong(request.getParameter("memberIdx"));
            memberService.deleteMember(memberIdx, response);
        } else {
            Long boardIdx = Long.parseLong(request.getParameter("boardIdx"));
            boardService.deleteBoard(boardIdx, response);
        }
    }
}
