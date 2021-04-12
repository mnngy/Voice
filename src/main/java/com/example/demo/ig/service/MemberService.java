package com.example.demo.ig.service;

import com.example.demo.ig.domain.Member;
import com.example.demo.ig.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        memberRepository.save(member);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
