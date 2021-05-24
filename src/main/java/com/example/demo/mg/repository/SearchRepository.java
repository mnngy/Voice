package com.example.demo.mg.repository;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Search;
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
public class SearchRepository {

    private final DataSource dataSource;

    @Autowired
    public SearchRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 전체 게시물 출력
     */
    public List<Search> Searchselect(String searchtext) throws SQLException{
        String sql = "select memberIdx, memberId from member where memberId like ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Search> searchList= new ArrayList<Search>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%"+searchtext+"%");
            rs = pstmt.executeQuery();
            while (rs.next()){
                Long memberIdx = rs.getLong(1);
                String memberId = rs.getString(2);
                searchList.add(new Search(memberIdx, memberId));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
        }
        return searchList;
    }



}
