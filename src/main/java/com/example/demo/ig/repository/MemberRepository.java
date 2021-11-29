package com.example.demo.ig.repository;

import com.example.demo.ig.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insertMember(Member member) {
        String sql = "insert into member(memberId, memberPassword) values(?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getMemberPassword());
    }

    public int memberSelectCountById(String memberId) throws SQLException {
        String sql = "select count(*) from member where memberId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }

    public int memberSelectPasswordById(Member member) {
        String sql = "select memberPassword from member where memberId = ?";
        String findPassword = jdbcTemplate.queryForObject(sql, String.class, member.getMemberId());

        if (member.getMemberPassword().equals(findPassword)) {
            return 1; // 로그인 성공
        } else {
            return 0; // 로그인 실패
        }
    }

    public List<Member> memberAllSelect() throws SQLException {
        String sql = "select * from member";
        List<Member> members = jdbcTemplate.query(sql, memberRowMapper());
        return members;
    }

    public Member memberSelectById(String memberId) {
        String sql = "select * from member where memberId = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
    }

    public Member memberSelectByIdx(Long memberIdx) throws SQLException {
        String sql = "select * from member where memberIdx = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberIdx);
    }

    public int memberUpdate(Member member) {
        String sql = "update member set memberImage = ?," +
                                      " memberId = ?," +
                                      "  memberPassword = ?," +
                                      " memberGrade = ?" +
                                   " where memberIdx = ?";
        int result = jdbcTemplate.update(sql,
                                         member.getMemberImage(),
                                         member.getMemberId(),
                                         member.getMemberPassword(),
                                         member.getMemberIdx());
        return result;
    }

    public int memberDeleteByIdx(Long memberIdx) throws SQLException {
        String sql = "update member set memberId = '@'," +
                                      " memberPassword = '@'," +
                                      " memberImage = '@'" +
                                   " where memberIdx = ?";
        int result = jdbcTemplate.update(sql, memberIdx);
        return result;
    }

    public int memberSelectGradeById(String memberId) throws SQLException {
        String sql = "select memberGrade from member where memberId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberIdx(rs.getLong("memberIdx"));
            member.setMemberImage(rs.getString("memberImage"));
            member.setMemberId(rs.getString("memberId"));
            member.setMemberId(rs.getString("memberPassword"));
            member.setMemberGrade(rs.getInt("memberGrade"));
            return member;
        };
    }
}
