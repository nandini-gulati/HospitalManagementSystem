package com.hms.dao;

import com.hms.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements CrudRepository<Patient> {

    @Override
    public boolean add(Patient p) {
        String sql = "INSERT INTO patients (name, phone, email, address, age, gender, blood_group) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getPhone());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getAddress());
            ps.setInt(5, p.getAge());
            ps.setString(6, p.getGender());
            ps.setString(7, p.getBloodGroup());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Patient p) {
        String sql = "UPDATE patients SET name=?, phone=?, email=?, address=?, age=?, gender=?, blood_group=? " +
                     "WHERE patient_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getPhone());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getAddress());
            ps.setInt(5, p.getAge());
            ps.setString(6, p.getGender());
            ps.setString(7, p.getBloodGroup());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM patients WHERE patient_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Patient findById(int id) {
        String sql = "SELECT * FROM patients WHERE patient_id=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("patient_id"));
                p.setName(rs.getString("name"));
                p.setPhone(rs.getString("phone"));
                p.setEmail(rs.getString("email"));
                p.setAddress(rs.getString("address"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setBloodGroup(rs.getString("blood_group"));
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("patient_id"));
                p.setName(rs.getString("name"));
                p.setPhone(rs.getString("phone"));
                p.setEmail(rs.getString("email"));
                p.setAddress(rs.getString("address"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setBloodGroup(rs.getString("blood_group"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔍 NEW: search by name (for PatientManagementFrame)
    public List<Patient> findByName(String name) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE name LIKE ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("patient_id"));
                p.setName(rs.getString("name"));
                p.setPhone(rs.getString("phone"));
                p.setEmail(rs.getString("email"));
                p.setAddress(rs.getString("address"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setBloodGroup(rs.getString("blood_group"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
