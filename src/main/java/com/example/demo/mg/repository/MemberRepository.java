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

    public Member save(Member member) {
        String sql = "insert into member(memberId, memberName, memberPassword) values(?, ?, ?)";

        Connection conn;
        PreparedStatement pstmt;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, member.getMemberId());
            pstmt.setString(2, member.getMemberName());
            pstmt.setString(3, member.getMemberPassword());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close
        }
        return member;
    }

    public List<Member> findAll() {
        String sql = "select * from member";

        List<Member> list = new ArrayList<>();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new Member(
                        rs.getLong("memberId"),
                        rs.getString("memberName"),
                        rs.getString("memberPassword")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // close
        }
        return list;
    }
}
