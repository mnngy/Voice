package com.example.demo.mg.repository;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;


@Repository
public class DetailRepository {

    private final DataSource dataSource;

    @Autowired
    public DetailRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 게시물 출력
     */
    public Board DetailBoardselect(Board board) throws SQLException{
        String sql = "select boardImage, boardAudio, memberId, boardDate from board, member where boardIdx=? and member.memberIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //List<Board> boardList= new ArrayList<Board>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, board.getBoardIdx());
            pstmt.setLong(2, 1);
            rs = pstmt.executeQuery();
            while (rs.next()){
                board.setBoardImage(rs.getString(1));
                board.setBoardAudio(rs.getString(2));
                board.setMemberId(rs.getString(3));
                board.setBoardDate(rs.getString(4));
                /*
                String boardImage = rs.getString(1);
                String boardAudio = rs.getString(2);
                String memberId = rs.getString(3);
                String boardDate = rs.getString(4);
                boardList.add(new Board(boardImage, boardAudio, memberId, boardDate));*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return board;
    }
    /**
     * 댓글 출력
     */
    public List<Comment> Detailcommentselect(Board board) throws SQLException{
        //String sql = "select * from board_comment where boardIdx = ?";
        String sql = "select memberId, commentText, commentDate from board_comment, member where board_comment.memberIdx=? and member.memberIdx=? and board_comment.boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> commentList= new ArrayList<Comment>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, 1);
            pstmt.setLong(3, board.getBoardIdx());
            rs = pstmt.executeQuery();
            while (rs.next()){
                String memberId = rs.getString(1);
                String commentText = rs.getString(2);
                String commentDate = rs.getString(3);
                commentList.add(new Comment(commentText, memberId, commentDate));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return commentList;
    }
    /**
     * 댓글 입력
     */
    public void setComment(Comment comment, Board board) throws SQLException {
        String sql = "insert into board_comment(commentText, memberIdx, boardIdx) values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getCommentText());
            pstmt.setLong(2, 1);
            pstmt.setLong(3, board.getBoardIdx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

    public int serchLike(Board board) throws SQLException{

        String sql = "select count(*) from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, board.getBoardIdx());
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("count(*)"); // 0: 존재하지 않는 아이디, 1: 중복된 아이디
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return -1;
    }
    public void insertLike(Board board) throws SQLException {
        String sql = "insert into likes(memberIdx,boardIdx) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, board.getBoardIdx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public void deleteLike(Board board) throws SQLException {
        String sql = "delete from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, board.getBoardIdx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public int countLike(Board board) throws SQLException{
        String sql = "select count(*) from likes where boardIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, board.getBoardIdx());
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("count(*)"); // 0: 존재하지 않는 아이디, 1: 중복된 아이디
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return -1;
    }

    public int serchFollow() throws SQLException{

        String sql = "select count(*) from follow where memberIdxFollower=? and memberIdxFollowing=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);    //  로그인 세션
            pstmt.setLong(2, 2);    //  게시글 작성자
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("count(*)"); // 0: 존재하지 않는 아이디, 1: 중복된 아이디
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return -1;
    }
    public void insertFollow() throws SQLException {
        String sql = "insert into follow(memberIdxFollower,memberIdxFollowing) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);    // 로그인 세션
            pstmt.setLong(2, 2);    // 게시글 작성자
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public void deleteFollow() throws SQLException {
        String sql = "delete from follow where memberIdxFollower = ? and memberIdxFollowing = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, 2);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

}
