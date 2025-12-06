package com.hms.dao;

import com.hms.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao implements CrudRepository<Doctor> {

    @Override
    public boolean add(Doctor d) {
        String sql = "INSERT INTO doctors (name, phone, email, specialization, experience_years, consultation_fee) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getEmail());
            ps.setString(4, d.getSpecialization());
            ps.setInt(5, d.getExperienceYears());
            ps.setInt(6, d.getConsultationFee());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Doctor d) {
        String sql = "UPDATE doctors SET name=?, phone=?, email=?, specialization=?, " +
                     "experience_years=?, consultation_fee=? WHERE doctor_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getEmail());
            ps.setString(4, d.getSpecialization());
            ps.setInt(5, d.getExperienceYears());
            ps.setInt(6, d.getConsultationFee());
            ps.setInt(7, d.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating doctor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM doctors WHERE doctor_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting doctor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Doctor findById(int id) {
        String sql = "SELECT * FROM doctors WHERE doctor_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("doctor_id"));
                d.setName(rs.getString("name"));
                d.setPhone(rs.getString("phone"));
                d.setEmail(rs.getString("email"));
                d.setSpecialization(rs.getString("specialization"));
                d.setExperienceYears(rs.getInt("experience_years"));
                d.setConsultationFee(rs.getInt("consultation_fee"));
                return d;
            }

        } catch (SQLException e) {
            System.err.println("Error finding doctor: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("doctor_id"));
                d.setName(rs.getString("name"));
                d.setPhone(rs.getString("phone"));
                d.setEmail(rs.getString("email"));
                d.setSpecialization(rs.getString("specialization"));
                d.setExperienceYears(rs.getInt("experience_years"));
                d.setConsultationFee(rs.getInt("consultation_fee"));
                list.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error listing doctors: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Optional: search by name
    public List<Doctor> findByName(String name) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE name LIKE ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("doctor_id"));
                d.setName(rs.getString("name"));
                d.setPhone(rs.getString("phone"));
                d.setEmail(rs.getString("email"));
                d.setSpecialization(rs.getString("specialization"));
                d.setExperienceYears(rs.getInt("experience_years"));
                d.setConsultationFee(rs.getInt("consultation_fee"));
                list.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error searching doctors: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
