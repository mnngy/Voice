package com.example.demo.ig.repository;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("ig.BoardRepository")
@RequiredArgsConstructor
@Slf4j
public class BoardRepository {

    private final DataSource dataSource;

    /**
     * 모든 게시글 조회
     */
    public List<Board> boardSelectAll() throws SQLException {
        String sql = "select * from board";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boards = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            boards = new ArrayList<>();

            while(rs.next()) {
                Board board = new Board();
                board.setBoardIdx(rs.getLong("boardIdx"));
                board.setBoardImage(rs.getString("boardImage"));
                board.setBoardAudio(rs.getString("boardAudio"));
                board.setMemberIdx(rs.getInt("memberIdx"));
                board.setBoardDate(rs.getString("boardDate"));
                boards.add(board);
            }
        }
        catch (Exception e) {
            log.error(this.getClass().getName() + "." + "boardSelectAll" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return boards;
    }

    /**
     * 특정 회원 게시글 조회
     */
    public List<Board> boardsSelectById(Long memberIdx) throws SQLException {
        String sql = "select * from board where memberIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boards = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberIdx);
            rs = pstmt.executeQuery();

            boards = new ArrayList<>();

            while(rs.next()) {
                Board board = new Board();
                board.setBoardIdx(rs.getLong("boardIdx"));
                board.setBoardImage(rs.getString("boardImage"));
                board.setBoardAudio(rs.getString("boardAudio"));
                board.setMemberIdx(rs.getInt("memberIdx"));
                board.setBoardDate(rs.getString("boardDate"));
                boards.add(board);
            }
        }
        catch (Exception e) {
            log.error(this.getClass().getName() + "." + "boardsSelectById" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return boards;
    }

    /**
     * 특정 게시글을 게시글 인덱스를 통해 조회
     */
    public Board boardSelectById(Long boardIdx) throws SQLException {
        String sql = "select * from board where boardIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Board board = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardIdx);
            rs = pstmt.executeQuery();
            rs.next();
            board = new Board();
            board.setBoardIdx(rs.getLong("boardIdx"));
            board.setBoardImage(rs.getString("boardImage"));
            board.setBoardAudio(rs.getString("boardAudio"));
            board.setMemberIdx(rs.getInt("memberIdx"));
            board.setBoardDate(rs.getString("boardDate"));
        }
        catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "boardSelectById" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return board;
    }

    /**
     * 특정 게시글을 게시글 인덱스를 통해 삭제
     */
    public int boardDeleteByIdx(Long boardIdx) throws SQLException {
        String sql = "update board set boardImage = '✕', boardAudio = '✕' where boardIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardIdx);
            result = pstmt.executeUpdate();
        }
        catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "boardDeleteByIdx" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return result;
    }

    /**
     * 특정 게시글 정보를 업데이트
     */
    public int boardUpdate(Board board) throws SQLException {
        String sql = "update board set boardAudio = ?, boardImage = ? where boardIdx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getBoardImage());
            pstmt.setString(2, board.getBoardAudio());
            pstmt.setLong(3, board.getBoardIdx());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "boardUpdate" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return result;
    }
}
