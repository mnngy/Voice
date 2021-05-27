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
public class FileUploadRepository {
    private final DataSource dataSource;

    @Autowired
    public FileUploadRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insetUpload(HttpServletRequest request, MultipartFile file,MultipartFile file2) throws SQLException{


        String whami;
        HttpSession session = request.getSession();
        whami =(String) session.getAttribute("sessionMemberId");



        String idx = "select memberIdx from member where memberId=?";
        String sql = "insert into board(boardImage,boardAudio,memberIdx) values(?,?,?)";


        ResultSet rs= null;
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(idx);
            pstmt.setString(1, whami);//session
            rs = pstmt.executeQuery();
            rs.next();
            Long mIdx = rs.getLong(1);


            conn = null;
            pstmt = null;


            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "../../../../upload-dir/"+whami+file.getOriginalFilename());
            pstmt.setString(2,"../../../../upload-dir/"+whami+file2.getOriginalFilename() );
            pstmt.setLong(3,mIdx);
            pstmt.executeUpdate();

            System.out.println(mIdx);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
            if (rs != null) {rs.close();}
        }

    }
}
