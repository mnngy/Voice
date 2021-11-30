package com.example.demo.ig.mapper;


import com.example.demo.ig.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int memberSelectCountById(String memberId);

    void insertMember(Member member);

    String memberSelectPasswordById(Member member);
}
