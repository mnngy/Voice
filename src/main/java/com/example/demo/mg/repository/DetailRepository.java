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
            pstmt.setLong(1, 3);
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
    public List<Comment> Detailcommentselect(Comment comment) throws SQLException{
        //String sql = "select * from board_comment where boardIdx = ?";
        String sql = "select memberId, commentText, commentDate from board_comment, member where board_comment.memberIdx=? and member.memberIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> commentList= new ArrayList<Comment>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, 1);
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
    public void setComment(Comment comment) throws SQLException {
        String sql = "insert into board_commet(commetText, memberIdx, boardIdx, commetDate) values(?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.KOREA);
       // Date time = new Date();
        Timestamp ts = localDateTimeToTimeStamp(LocalDateTime.now());
        //long systemTime = System.currentTimeMillis();
        String StDate = format.format(ts);
        //System.out.println(ts);
        java.sql.Date sqldate = java.sql.Date.valueOf(StDate);

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getCommentText());
            pstmt.setLong(2, 1);
            pstmt.setLong(3, 1);
            pstmt.setDate(4, sqldate);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public static Timestamp localDateTimeToTimeStamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt); // 2018-07-26 01:06:55.323
    }

}
