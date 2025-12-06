package com.hms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";              // your MySQL username
    private static final String PASSWORD = "user1234"; // your MySQL password

    static {
        try {
            // Load MySQL JDBC driver (error handling here)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load MySQL JDBC Driver. Check your JAR file.");
            e.printStackTrace(); // for debugging
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Error handling: log message & rethrow so UI can show popup
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }
}
