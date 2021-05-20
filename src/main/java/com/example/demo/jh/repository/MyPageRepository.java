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
     * 전체 게시물 출력
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
            pstmt.setString(1, "aaa");//session

            rs = pstmt.executeQuery();
            while (rs.next()){
                String boardAudio = rs.getString(1);
                String boardImg = rs.getString(2);
                String boardDate = rs.getString(3);
                String memberID = rs.getString(4);
                Long boardIdx = rs.getLong(5);
                System.out.println(boardAudio+" "+boardImg);
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
}
