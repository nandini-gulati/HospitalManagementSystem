package com.hms.ui;

import com.hms.dao.DoctorDao;
import com.hms.exception.InvalidDataException;
import com.hms.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DoctorManagementFrame extends JFrame {

    private JTextField txtId, txtName, txtPhone, txtEmail, txtSpec, txtExp, txtFee, txtSearch;
    private JTable table;
    private final DoctorDao doctorDao = new DoctorDao();

    public DoctorManagementFrame() {
        setTitle("Manage Doctors");
        setSize(950, 550);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 50, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(80, 20, 80, 25);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 60, 50, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(80, 60, 150, 25);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(20, 100, 50, 25);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(80, 100, 150, 25);
        add(txtPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 140, 50, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(80, 140, 150, 25);
        add(txtEmail);

        JLabel lblSpec = new JLabel("Special:");
        lblSpec.setBounds(20, 180, 60, 25);
        add(lblSpec);
        txtSpec = new JTextField();
        txtSpec.setBounds(80, 180, 150, 25);
        add(txtSpec);

        JLabel lblExp = new JLabel("Exp (yrs):");
        lblExp.setBounds(20, 220, 70, 25);
        add(lblExp);
        txtExp = new JTextField();
        txtExp.setBounds(90, 220, 140, 25);
        add(txtExp);

        JLabel lblFee = new JLabel("Fee:");
        lblFee.setBounds(20, 260, 50, 25);
        add(lblFee);
        txtFee = new JTextField();
        txtFee.setBounds(80, 260, 150, 25);
        add(txtFee);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 310, 80, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 310, 90, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(210, 310, 90, 30);
        add(btnDelete);

        JLabel lblSearch = new JLabel("Search Name:");
        lblSearch.setBounds(20, 360, 90, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(110, 360, 150, 25);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(270, 360, 90, 25);
        add(btnSearch);

        JButton btnShowAll = new JButton("Show All");
        btnShowAll.setBounds(370, 360, 100, 25);
        add(btnShowAll);

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(300, 20, 620, 350);
        add(scroll);

        loadDoctors();

        btnAdd.addActionListener(e -> {
            try {
                addDoctor();
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Unexpected error while adding: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                updateDoctor();
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Unexpected error while updating: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                deleteDoctor();
            } catch (Exception ex) {
                showError("Unexpected error while deleting: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                searchDoctors();
            } catch (Exception ex) {
                showError("Error while searching: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnShowAll.addActionListener(e -> {
            try {
                loadDoctors();
            } catch (Exception ex) {
                showError("Error while loading all doctors: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void updateTable(List<Doctor> doctors) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Name", "Phone", "Email", "Specialization", "Experience", "Fee"}, 0);

        for (Doctor d : doctors) {
            model.addRow(new Object[]{
                    d.getId(), d.getName(), d.getPhone(), d.getEmail(),
                    d.getSpecialization(), d.getExperienceYears(), d.getConsultationFee()
            });
        }
        table.setModel(model);
    }

    private void loadDoctors() {
        List<Doctor> doctors = doctorDao.findAll();
        updateTable(doctors);
    }

    private void searchDoctors() {
        String name = txtSearch.getText().trim();
        if (name.isEmpty()) {
            showError("Enter a name to search.");
            return;
        }
        List<Doctor> results = doctorDao.findByName(name);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No doctors found with that name.");
        }
        updateTable(results);
    }

    private Doctor createDoctorFromForm(boolean requireId) throws InvalidDataException {
        Doctor d = new Doctor();

        if (requireId) {
            if (txtId.getText().trim().isEmpty())
                throw new InvalidDataException("Doctor ID is required for update/delete.");
            try {
                d.setId(Integer.parseInt(txtId.getText().trim()));
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Doctor ID must be a number.");
            }
        }

        String name = txtName.getText().trim();
        String expStr = txtExp.getText().trim();
        String feeStr = txtFee.getText().trim();

        if (name.isEmpty()) throw new InvalidDataException("Name cannot be empty.");
        if (expStr.isEmpty()) throw new InvalidDataException("Experience is required.");
        if (feeStr.isEmpty()) throw new InvalidDataException("Fee is required.");

        int exp, fee;
        try {
            exp = Integer.parseInt(expStr);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Experience must be a number.");
        }

        try {
            fee = Integer.parseInt(feeStr);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Fee must be a number.");
        }

        d.setName(name);
        d.setPhone(txtPhone.getText().trim());
        d.setEmail(txtEmail.getText().trim());
        d.setSpecialization(txtSpec.getText().trim());
        d.setExperienceYears(exp);
        d.setConsultationFee(fee);

        return d;
    }

    private void addDoctor() throws InvalidDataException {
        Doctor d = createDoctorFromForm(false);
        if (doctorDao.add(d)) {
            JOptionPane.showMessageDialog(this, "Doctor added.");
            loadDoctors();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding doctor.");
        }
    }

    private void updateDoctor() throws InvalidDataException {
        Doctor d = createDoctorFromForm(true);
        if (doctorDao.update(d)) {
            JOptionPane.showMessageDialog(this, "Doctor updated.");
            loadDoctors();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating doctor.");
        }
    }

    private void deleteDoctor() {
        if (txtId.getText().trim().isEmpty()) {
            showError("Enter Doctor ID to delete.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            showError("Doctor ID must be a number.");
            return;
        }

        if (doctorDao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Doctor deleted.");
            loadDoctors();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting doctor.");
        }
    }
}
