package com.example.demo.ig.repository;

import com.example.demo.ig.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    @Autowired
    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 회원가입
     */
    public void memberSave(Member member) throws SQLException {
        String sql = "insert into member(memberId, memberPassword) values(?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getMemberPassword());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

    /**
     * 아이디 중복 확인
     */
    public int memberIdDuplicateCheck(String memberId) throws SQLException {
        String sql = "select count(*) from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("count(*)"); // 0: 존재하지 않는 아이디, 1: 중복된 아이디
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return -1;
    }

    /**
     * 로그인
     */
    public int memberSelect(Member member) throws SQLException {
        String sql = "select memberPassword from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("memberPassword").equals(member.getMemberPassword())) {
                    return 1; // 로그인 성공
                } else {
                    return 0; // 비밀번호 불일치
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return -1; // 아이디 불일치
    }
}
