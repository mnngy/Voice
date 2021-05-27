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


    public int Writerselect(Long memberIdx, Board board) throws SQLException {
        String sql = "select count(*) from board where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx); // 로그인 세션
            pstmt.setLong(2, board.getBoardIdx()); // 로그인 세션
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
    public Long MemberIDXselect(String id) throws SQLException {
        String sql = "select memberIdx from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long idx = new Long(0);
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id); // 로그인 세션
            rs = pstmt.executeQuery();
            rs.next();
            idx = rs.getLong("memberIdx");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return idx;
    }
    public Long BoardIDXselect(Board board) throws SQLException {
        String sql = "select memberIdx from member where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long idx = new Long(0);
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getMemberId()); // 로그인 세션
            rs = pstmt.executeQuery();
            rs.next();
            idx = rs.getLong("memberIdx");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return idx;
    }
    public Long BoardIDXselect2(Long boardidx) throws SQLException {
        String sql = "select memberIdx from board where boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long idx = new Long(0);
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardidx); // 로그인 세션
            rs = pstmt.executeQuery();
            rs.next();
            idx = rs.getLong("memberIdx");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return idx;
    }
    public Long BoardIDXselect3(String memberId) throws SQLException {
        String sql = "select memberIdx from member where memberId=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long idx = new Long(0);
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId); // 로그인 세션
            rs = pstmt.executeQuery();
            rs.next();
            idx = rs.getLong("memberIdx");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return idx;
    }
    /**
     * 게시물 출력
     */
    public Board DetailBoardselect(Board board) throws SQLException{
        String sql = "select boardImage, boardAudio, memberId, boardDate from board, member where boardIdx=? and member.memberIdx=board.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, board.getBoardIdx());
            rs = pstmt.executeQuery();
            while (rs.next()){
                board.setBoardImage(rs.getString(1));
                board.setBoardAudio(rs.getString(2));
                board.setMemberId(rs.getString(3));
                board.setBoardDate(rs.getString(4));
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
        String sql = "select memberId, commentText, commentDate from board_comment, member where boardIdx = ? and board_comment.memberIdx=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> commentList= new ArrayList<Comment>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, board.getBoardIdx());
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
    public void setComment(Comment comment, Board board, Long memberIdx) throws SQLException {
        String sql = "insert into board_comment(commentText, memberIdx, boardIdx) values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getCommentText());
            pstmt.setLong(2, memberIdx);
            pstmt.setLong(3, board.getBoardIdx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

    public int serchLike(Board board, Long memberIdx) throws SQLException{

        String sql = "select count(*) from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
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
    public void insertLike(Board board, Long memberIdx) throws SQLException {
        String sql = "insert into likes(memberIdx,boardIdx) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
            pstmt.setLong(2, board.getBoardIdx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public void deleteLike(Board board, Long memberIdx) throws SQLException {
        String sql = "delete from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
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

    public int serchFollow(Long memberIdx, Long boardmember) throws SQLException{

        String sql = "select count(*) from follow where memberIdxFollower=? and memberIdxFollowing=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardmember);    //  로그인 세션
            pstmt.setLong(2, memberIdx);    //  게시글 작성자
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
    public void insertFollow(Long memberIdx, Long boardmember) throws SQLException {
        String sql = "insert into follow(memberIdxFollower,memberIdxFollowing) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardmember);    // 게시글 작성자
            pstmt.setLong(2, memberIdx);    // 로그인 세션
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public void deleteFollow(Long memberIdx, Long boardmember) throws SQLException {
        String sql = "delete from follow where memberIdxFollower = ? and memberIdxFollowing = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardmember);
            pstmt.setLong(2, memberIdx);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

}
