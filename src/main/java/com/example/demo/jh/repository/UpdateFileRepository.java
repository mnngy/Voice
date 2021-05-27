package com.example.demo.jh.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UpdateFileRepository {
    private final DataSource dataSource;

    @Autowired
    public UpdateFileRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void updateUpload(HttpServletRequest request, MultipartFile file, MultipartFile file2,int boardIdx) throws SQLException{


        String whami;
        HttpSession session = request.getSession();
        whami =(String) session.getAttribute("sessionMemberId");


        String sql = "update board set boardImage=?,boardAudio=? where boardIdx=?;";


        ResultSet rs= null;
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "upload-dir/"+whami+file.getOriginalFilename());
            pstmt.setString(2,"upload-dir/"+whami+file2.getOriginalFilename() );
            pstmt.setInt(3,boardIdx);
            pstmt.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
            if (rs != null) {rs.close();}
        }

    }

    public void deleteFile(int boardIdx)throws SQLException{
        String sql = "delete from board where boardIdx=?;";


        ResultSet rs= null;
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,boardIdx);
            pstmt.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
            if (rs != null) {rs.close();}
        }

    }
}
