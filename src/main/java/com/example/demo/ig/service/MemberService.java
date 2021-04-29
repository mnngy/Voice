package com.example.demo.ig.service;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            /*
                중복 아이디 제한 구현
             */
            memberRepository.memberSave(member);
        } catch (SQLException throwables) {
            // 데이터베이스 오류
            throwables.printStackTrace();
        }
    }

    /**
     * 로그인 서비스
     */
    public void memberLogin(Member member) {
        /*
        데이터베이스 조회 후 전체게시판 페이지로 이동, 세션 추가
         */
    }
}
