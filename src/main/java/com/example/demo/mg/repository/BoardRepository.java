package com.example.demo.mg.repository;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import com.example.demo.mg.domain.Like;
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
public class BoardRepository {

    private final DataSource dataSource;

    @Autowired
    public BoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 전체 게시물 출력
     */
    public List<Board> Boardselect() throws SQLException{
        String sql = "select boardIdx, memberId, boardImage, boardAudio, boardDate from board, member where board.memberIdx=member.memberIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boardList= new ArrayList<Board>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Long boardIdx = rs.getLong(1);
                String memberId = rs.getString(2);
                String boardImg = rs.getString(3);
                String boardAudio = rs.getString(4);
                String boardDate = rs.getString(5);
                boardList.add(new Board(boardIdx, memberId, boardImg, boardAudio, boardDate, new Long(0)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return boardList;
    }
    public List<Like> serchLikeList() throws SQLException{

        String sql = "select boardIdx, count(*) from likes group by boardIdx";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Like> likeList = new ArrayList<Like>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Long boardIdx = rs.getLong("boardIdx");
                Long likecount = rs.getLong("count(*)");
                System.out.println(boardIdx+"   "+likecount);
                likeList.add(new Like(boardIdx,likecount));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }

        return likeList;
    }
    public int countLike2(Long boardIdx) throws SQLException{
        String sql = "select count(*) from likes where boardIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardIdx);
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
    public int serchLike2(Long boardIdx) throws SQLException{

        String sql = "select count(*) from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, boardIdx);
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
    public void insertLike2(Long boardIdx) throws SQLException {
        String sql = "insert into likes(memberIdx,boardIdx) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, boardIdx);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }
    public void deleteLike2(Long boardIdx) throws SQLException {
        String sql = "delete from likes where memberIdx=? and boardIdx=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, 1);
            pstmt.setLong(2, boardIdx);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
    }

}
