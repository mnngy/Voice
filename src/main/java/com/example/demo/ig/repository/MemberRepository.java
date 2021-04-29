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
     * 로그인
     */
    public void memberSelect(Member member) throws SQLException {
        String sql = "select memberPassword from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            rs = pstmt.executeQuery();

            // 아이디 있음
            if (rs.next()) {
                if (rs.getString("memberPassword").equals(member.getMemberPassword())) {
                    // 로그인 성공
                } else {
                    // 비밀번호 불일치
                }
            }
            // 아이디 불일치
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
}
