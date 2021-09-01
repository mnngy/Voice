package com.example.demo.ig.controller;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.domain.Member;
import com.example.demo.ig.service.BoardService;
import com.example.demo.ig.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("admin")
    public String Home(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
            return "admin/admin";
        } else {
            return "login";
        }
    }

    @GetMapping("admin/all")
    public String all(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
            List<Member> members = memberService.findAllMember();
            List<Board> boards = boardService.findAllBoard();
            model.addAttribute("members", members);
            model.addAttribute("boards", boards);
            return "admin/all";
        } else {
            return "login";
        }
    }

    @GetMapping("admin/search")
    public String search(HttpServletRequest request, HttpServletResponse response, Model model) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
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
        } else {
            return "login";
        }
    }

    @GetMapping("admin/edit")
    public String edit(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
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
        } else {
            return "login";
        }
    }

    @PostMapping("admin/editMember")
    public void editMember(Member member, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
            memberService.editMember(member, response);
        } else {
            return;
        }
    }

    @PostMapping("admin/editBoard")
    public void editBoard(Board board, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
            boardService.editBoard(board, response);
        } else {
            return;
        }
    }

    @GetMapping("admin/delete")
    public void String(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("sessionMemberId");

        int result = memberService.checkAdmin(memberId);

        if (result == 1) {
            if (request.getParameter("memberIdx") != null) {
                Long memberIdx = Long.parseLong(request.getParameter("memberIdx"));
                memberService.deleteMember(memberIdx, response);
            } else {
                Long boardIdx = Long.parseLong(request.getParameter("boardIdx"));
                boardService.deleteBoard(boardIdx, response);
            }
        } else {
            return;
        }

    }
}
