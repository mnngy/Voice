package com.example.demo.ig.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int memberSelectCountById(String memberId);
}
