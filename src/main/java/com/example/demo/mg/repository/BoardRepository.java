package com.example.demo.mg.repository;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
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
    public List<Board> Boardselect(Board board) throws SQLException{
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
                boardList.add(new Board(boardIdx, memberId, boardImg, boardAudio, boardDate));
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



}
