package com.hms.main;

import com.hms.dao.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void main(String[] args) {
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement()) {

            String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    role VARCHAR(20) NOT NULL
                );
                """;

            String createPatients = """
                CREATE TABLE IF NOT EXISTS patients (
                    patient_id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    phone VARCHAR(15),
                    email VARCHAR(100),
                    address VARCHAR(200),
                    age INT,
                    gender VARCHAR(10),
                    blood_group VARCHAR(10)
                );
                """;

            String createDoctors = """
                CREATE TABLE IF NOT EXISTS doctors (
                    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    phone VARCHAR(15),
                    email VARCHAR(100),
                    specialization VARCHAR(100),
                    experience_years INT,
                    consultation_fee INT
                );
                """;

            String insertAdmin = """
                INSERT INTO users (username, password, role)
                VALUES ('admin', 'admin', 'ADMIN')
                ON DUPLICATE KEY UPDATE username = username;
                """;

            st.execute(createUsers);
            st.execute(createPatients);
            st.execute(createDoctors);
            st.execute(insertAdmin);

            System.out.println("Database tables initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
