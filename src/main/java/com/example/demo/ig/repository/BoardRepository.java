package com.example.demo.ig.repository;

import com.example.demo.ig.domain.Board;
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
public class BoardRepository {

    private final DataSource dataSource;

    @Autowired
    public BoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
                board.setBoardDate(rs.getDate("boardDate"));
                boards.add(board);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
    public List<Board> boardSelectById(Long memberIdx) throws SQLException {
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
                board.setBoardDate(rs.getDate("boardDate"));
                boards.add(board);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return boards;
    }
}
