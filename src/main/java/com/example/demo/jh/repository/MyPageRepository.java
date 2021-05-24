package com.example.demo.jh.repository;

import com.example.demo.jh.domain.MyPageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MyPageRepository {

    private final DataSource dataSource;

    @Autowired
    public MyPageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 내 게시물
     */
    public List<MyPageBoard> MyPage(String ID) throws SQLException {
        String sql = "select boardAudio, boardImage, boardDate, memberId, boardIdx from member, board where member.memberId=? and board.memberIdx=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<MyPageBoard> myPageBoardList = new ArrayList<MyPageBoard>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);//session

            rs = pstmt.executeQuery();
            while (rs.next()){
                String boardAudio = rs.getString(1);
                String boardImg = rs.getString(2);
                String boardDate = rs.getString(3);
                String memberID = rs.getString(4);
                Long boardIdx = rs.getLong(5);

                myPageBoardList.add(new MyPageBoard(boardAudio, boardImg, boardDate, memberID,boardIdx));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return myPageBoardList;
    }



    public Long Count(String ID) throws SQLException {
        String sql = "select count(boardIdx) from board, member where member.memberId=? and board.memberIdx=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long count = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);//session

            rs = pstmt.executeQuery();
            rs.next();
            count = rs.getLong(1);



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return count;
    }

    public Long Follower(String ID) throws SQLException {
        String sql = "select count(*) from follow,member  where member.memberId=? and follow.memberIdxFollower=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long follower = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);//session

            rs = pstmt.executeQuery();
            rs.next();
            follower = rs.getLong(1);



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return follower;
    }

    public Long Following(String ID) throws SQLException {
        String sql = "select count(*) from follow,member  where member.memberId=? and follow.memberIdxFollowing=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long following = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);//session

            rs = pstmt.executeQuery();
            rs.next();
            following = rs.getLong(1);



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return following;
    }
}
