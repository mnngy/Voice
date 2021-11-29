package com.example.demo.ig.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("select count(*) from member where memberId = #{memberId}")
    int memberSelectCountById(String memberId);
}
