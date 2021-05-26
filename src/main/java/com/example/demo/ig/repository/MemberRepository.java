package com.example.demo.ig.repository;

import com.example.demo.ig.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public int memberSelectCountById(String memberId) throws SQLException {
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
    public int memberSelectPasswordById(Member member) throws SQLException {
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

    /**
     * 모든 회원을 리스트로 반환
     */
    public List<Member> memberAllSelect() throws SQLException {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Member> members = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            members = new ArrayList<>();

            while(rs.next()) {
                Member member = new Member();
                member.setMemberIdx(rs.getLong("memberIdx"));
                member.setMemberImage(rs.getString("memberImage"));
                member.setMemberId(rs.getString("memberId"));
                member.setMemberPassword(rs.getString("memberPassword"));
                member.setMemberGrade(rs.getInt("memberGrade"));
                members.add(member);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return members;
    }

    /**
     * 특정 회원 정보를 아이디를 통해 조회
     */
    public Member memberSelectById(String memberId) throws SQLException {
        String sql = "select * from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setMemberIdx(rs.getLong("memberIdx"));
                member.setMemberImage(rs.getString("memberImage"));
                member.setMemberId(rs.getString("memberId"));
                member.setMemberPassword(rs.getString("memberPassword"));
                member.setMemberGrade(rs.getInt("memberGrade"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return member;
    }

    /**
     * 특정 회원 정보를 인덱스를 통해 조회
     */
    public Member memberSelectByIdx(Long memberIdx) throws SQLException {
        String sql = "select * from member where memberIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setMemberIdx(rs.getLong("memberIdx"));
                member.setMemberImage(rs.getString("memberImage"));
                member.setMemberId(rs.getString("memberId"));
                member.setMemberPassword(rs.getString("memberPassword"));
                member.setMemberGrade(rs.getInt("memberGrade"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return member;
    }

    /**
     * 특정 회원 정보를 업데이트
     */
    public int memberUpdate(Member member) throws SQLException {
        String sql = "update member set memberImage = ?, memberId = ?, memberPassword = ?, memberGrade = ? where memberIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberImage());
            pstmt.setString(2, member.getMemberId());
            pstmt.setString(3, member.getMemberPassword());
            pstmt.setInt(4, member.getMemberGrade());
            pstmt.setLong(5, member.getMemberIdx());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return result;
    }

    /**
     * 특정 회원 정보를 회원 인덱스를 통해 삭제
     */
    public int memberDeleteByIdx(Long memberIdx) throws SQLException {
        String sql = "update member set memberId = '✕', memberPassword = '✕', memberImage = '✕'" +
                " where memberIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
            result = pstmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return result; // 1일 때 성공
    }
}
