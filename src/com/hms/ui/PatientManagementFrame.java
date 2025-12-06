package com.hms.ui;

import com.hms.dao.PatientDao;
import com.hms.exception.InvalidDataException;
import com.hms.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class PatientManagementFrame extends JFrame {

    private JTextField txtId, txtName, txtPhone, txtEmail, txtAddress, txtAge, txtGender, txtBloodGroup;
    private JTextField txtSearch;
    private JTable table;
    private final PatientDao patientDao = new PatientDao();

    public PatientManagementFrame() {
        setTitle("Manage Patients");
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

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(20, 180, 60, 25);
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(80, 180, 150, 25);
        add(txtAddress);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(20, 220, 50, 25);
        add(lblAge);
        txtAge = new JTextField();
        txtAge.setBounds(80, 220, 150, 25);
        add(txtAge);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(20, 260, 60, 25);
        add(lblGender);
        txtGender = new JTextField();
        txtGender.setBounds(80, 260, 150, 25);
        add(txtGender);

        JLabel lblBG = new JLabel("Blood Grp:");
        lblBG.setBounds(20, 300, 70, 25);
        add(lblBG);
        txtBloodGroup = new JTextField();
        txtBloodGroup.setBounds(90, 300, 140, 25);
        add(txtBloodGroup);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 350, 80, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 350, 90, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(210, 350, 90, 30);
        add(btnDelete);

        // 🔍 Search controls
        JLabel lblSearch = new JLabel("Search Name:");
        lblSearch.setBounds(20, 400, 90, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(110, 400, 150, 25);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(270, 400, 90, 25);
        add(btnSearch);

        JButton btnShowAll = new JButton("Show All");
        btnShowAll.setBounds(370, 400, 100, 25);
        add(btnShowAll);

        // Table
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(300, 20, 620, 350);
        add(scroll);

        try {
            loadPatients();
        } catch (Exception e) {
            // error handling for any DB issue during initial load
            showError("Could not load patients: " + e.getMessage());
            e.printStackTrace();
        }

        btnAdd.addActionListener((ActionEvent e) -> {
            try {
                addPatient();
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Unexpected error while adding patient: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnUpdate.addActionListener((ActionEvent e) -> {
            try {
                updatePatient();
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Unexpected error while updating patient: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            try {
                deletePatient();
            } catch (Exception ex) {
                showError("Unexpected error while deleting patient: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                searchPatients();
            } catch (Exception ex) {
                showError("Error while searching: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnShowAll.addActionListener(e -> {
            try {
                loadPatients();
            } catch (Exception ex) {
                showError("Error while loading all patients: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // 🔁 Reusable method to refresh table
    private void updateTable(List<Patient> patients) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Name", "Phone", "Email", "Age", "Gender", "Blood Group"}, 0);

        for (Patient p : patients) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getPhone(), p.getEmail(),
                    p.getAge(), p.getGender(), p.getBloodGroup()
            });
        }
        table.setModel(model);
    }

    private void loadPatients() {
        List<Patient> patients = patientDao.findAll();
        updateTable(patients);
    }

    private void searchPatients() {
        String name = txtSearch.getText().trim();
        if (name.isEmpty()) {
            showError("Enter a name to search.");
            return;
        }
        List<Patient> results = patientDao.findByName(name);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients found with that name.");
        }
        updateTable(results);
    }

    private Patient createPatientFromForm(boolean requireId) throws InvalidDataException {
        Patient p = new Patient();

        if (requireId) {
            if (txtId.getText().trim().isEmpty())
                throw new InvalidDataException("Patient ID is required for update/delete.");
            try {
                p.setId(Integer.parseInt(txtId.getText().trim()));
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Patient ID must be a number.");
            }
        }

        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();

        if (name.isEmpty()) throw new InvalidDataException("Name cannot be empty.");
        if (ageStr.isEmpty()) throw new InvalidDataException("Age cannot be empty.");

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Age must be a number.");
        }

        p.setName(name);
        p.setPhone(txtPhone.getText().trim());
        p.setEmail(txtEmail.getText().trim());
        p.setAddress(txtAddress.getText().trim());
        p.setAge(age);
        p.setGender(txtGender.getText().trim());
        p.setBloodGroup(txtBloodGroup.getText().trim());

        return p;
    }

    private void addPatient() throws InvalidDataException {
        Patient p = createPatientFromForm(false);
        if (patientDao.add(p)) {
            JOptionPane.showMessageDialog(this, "Patient added.");
            loadPatients();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding patient.");
        }
    }

    private void updatePatient() throws InvalidDataException {
        Patient p = createPatientFromForm(true);
        if (patientDao.update(p)) {
            JOptionPane.showMessageDialog(this, "Patient updated.");
            loadPatients();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating patient.");
        }
    }

    private void deletePatient() {
        if (txtId.getText().trim().isEmpty()) {
            showError("Enter Patient ID to delete.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            showError("Patient ID must be a number.");
            return;
        }

        if (patientDao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Patient deleted.");
            loadPatients();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting patient.");
        }
    }
}
