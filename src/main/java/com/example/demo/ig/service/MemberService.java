package com.example.demo.ig.service;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입 서비스
     */
    public void memberjoin(Member member) {
        try {
            memberRepository.memberSave(member);
        } catch (SQLException throwables) {
            // 데이터베이스 오류
            throwables.printStackTrace();
        }
    }

    /**
     * 로그인 서비스
     */
    public void memberLogin(HttpServletRequest request, HttpServletResponse response, Member member) {

        response.setContentType("text/html;charset=UTF-8");
        String memberIdChk = "memberIdUnCheck";

        // 아이디 기억하기 버튼을 눌렀는지
        if (request.getParameter("memberIdRemember") != null) {
            memberIdChk = request.getParameter("memberIdRemember");
        }

        try {
            int result = memberRepository.memberSelect(member);

            // 로그인 성공
            if (result == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("sessionMemberId", member.getMemberId());
                System.out.print(session.getAttribute("sessionMemberId"));
                System.out.println(" 님이 로그인을 하셨습니다.");

                PrintWriter out = response.getWriter();
                out.println("<script>" +
                        "alert('로그인을 성공했습니다!');" +
                        "</script>");
                out.close();

                // 아이디 기억하기 버튼을 눌렀을 때 쿠키 저장
                if (memberIdChk.equals("memberIdCheck")) {
                    Cookie cookie = new Cookie("cookieMemberId", member.getMemberId());
                    cookie.setPath("/");
                    cookie.setMaxAge(60*60*24*30);
                    response.addCookie(cookie);
                    System.out.print(session.getAttribute("sessionMemberId"));
                    System.out.println(" 님이 쿠키를 생성했습니다. cookieMemberId: " + cookie.getValue());
                } else {
                    Cookie cookie = new Cookie("cookieMemberId", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }

                // 비밀번호 불일치
            } else if (result == 0) {
                PrintWriter out = response.getWriter();
                out.println("<script>" +
                        "alert('비밀번호가 일치하지 않습니다.');" +
                        "</script>");
                out.close();
            }
            // 아이디 불일치
            else {
                PrintWriter out = response.getWriter();
                out.println("<script>" +
                        "alert('아이디가 일치하지 않습니다.');" +
                        "</script>");
                out.close();
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
