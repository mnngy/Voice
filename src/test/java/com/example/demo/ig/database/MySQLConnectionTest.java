package com.example.demo.ig.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/voice?serverTimezone=UTC&characterEncoding=UTF-8";
    String id = "root";
    String pw = "miningyu";

    @Test
    void testConnection() {
        try {
            Class.forName(driver);
            try {
                Connection conn = DriverManager.getConnection(url, id, pw);
                System.out.println(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
