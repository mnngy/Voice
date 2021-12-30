package com.example.demo.ig.service;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.mapper.MemberMapper;
import com.example.demo.ig.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    /**
     * 회원가입 서비스
     */
    public void memberJoin(Member member) {
        memberMapper.insertMember(member);
        log.info("{} 님이 회원가입을 하셨습니다.", member.getMemberId());
    }

    /**
     * 아이디 중복 확인 서비스
     */
    public String memberIdDuplicateCheck(String memberId) {
        try {
            if (memberMapper.memberSelectCountById(memberId) == 0) {
                return "0"; // 회원가입 가능
            }
        } catch (Exception e) {
            log.error(this.getClass() + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return "1"; // 회원가입 불가
    }

    /**
     * 로그인 서비스
     */
    // TODO 예외처리 (서버에 데이터베이스가 문제가 있을 때)
    public void memberLogin(HttpServletRequest request, HttpServletResponse response, Member member) {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String findPassword = memberMapper.memberSelectPasswordById(member);

            if (findPassword.equals(member.getMemberPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("sessionMemberId", member.getMemberId());

                log.info("{}님이 로그인을 하셨습니다.", session.getAttribute("sessionMemberId"));

                PrintWriter out = response.getWriter();
                out.println("<script>" +
                                "alert('로그인을 성공했습니다!');" +
                                "location.href='/main';" +
                            "</script>");
                out.close();
            } else {
                PrintWriter out = response.getWriter();
                out.println("<script>" +
                                "alert('비밀번호가 일치하지 않습니다.');" +
                                "location.href='/';" +
                            "</script>");
                out.close();
            }
        } catch (Exception e) {
            log.warn(this.getClass().getName() + "." + "memberLogin" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException ex) {
                log.error(this.getClass().getName() + "." + "memberLogin" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
            }
            out.println("<script>" +
                            "alert('아이디가 일치하지 않습니다.');" +
                            "location.href='/';" +
                        "</script>");
        }
    }

    /**
     * 모든 회원 리스트를 가져오는 서비스
     */
    public List<Member> findAllMember() {
        List<Member> members = null;
        try {
            members = memberRepository.memberAllSelect();
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "findAllMember" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return members;
    }

    /**
     * 특정 회원을 회원 아이디를 통해 가져오는 서비스
     */
    public Member findMember(String memberId) {
        return memberRepository.memberSelectById(memberId);
    }

    /**
     * 특정 회원을 회원 인덱스를 통해 가져오는 서비스
     */
    public Member findMember(Long memberIdx) {
        Member member = null;
        try {
            member = memberRepository.memberSelectByIdx(memberIdx);
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "findMember(Long memberIdx)" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return member;
    }

    /**
     * 특정 회원 정보 수정하는 서비스
     */
    public void editMember(Member member, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/html;charset=euc-kr");
        int result = 0;

        try {
            result = memberRepository.memberUpdate(member);

            if (result == 1) {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('회원 정보를 수정했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            } else {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('값을 잘못 입력했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            }
        } catch (IOException e) {
            log.error(this.getClass().getName() + "." + "editMember" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
    }

    /**
     * 특정 회원을 회원 인덱스를 통해 삭제하는 서비스
     */
    public void deleteMember(Long memberIdx, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/html;charset=euc-kr");

        try {
            int result = memberRepository.memberDeleteByIdx(memberIdx);

            if (result == 1) {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('회원을 삭제했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            } else {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('회원 삭제를 실패했습니다.'); history.go(-1); location.href='/admin';" +
                        "</script>");
                out.close();
            }
        } catch (SQLException | IOException e) {
            log.error(this.getClass().getName() + "." + "deleteMember" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
    }

    /**
     * 관리자 인지 아닌지 확인하는 서비스
     */
    public int checkAdmin(String memberId) {
        PrintWriter out = null;
        int result = 0;

        try {
            result = memberRepository.memberSelectGradeById(memberId);
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "checkAdmin" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return result;
    }
}
