package com.hms.ui;

import com.hms.model.User;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    private final User loggedInUser;

    public DashboardFrame(User user) {
        this.loggedInUser = user;

        setTitle("Hospital Management System - Dashboard");
        setSize(750, 450);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        lbl.setBounds(220, 20, 300, 30);
        add(lbl);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(600, 20, 100, 30);
        add(btnLogout);
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JButton btnPatients = new JButton("Manage Patients");
        btnPatients.setBounds(50, 80, 200, 40);
        add(btnPatients);

        JButton btnDoctors = new JButton("Manage Doctors");
        btnDoctors.setBounds(50, 140, 200, 40);
        add(btnDoctors);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuManage = new JMenu("Manage");
        JMenuItem menuPatients = new JMenuItem("Patients");
        JMenuItem menuDoctors = new JMenuItem("Doctors");
        menuManage.add(menuPatients);
        menuManage.add(menuDoctors);
        menuBar.add(menuManage);
        setJMenuBar(menuBar);

        btnPatients.addActionListener(e -> new PatientManagementFrame().setVisible(true));
        menuPatients.addActionListener(e -> new PatientManagementFrame().setVisible(true));

        btnDoctors.addActionListener(e -> new DoctorManagementFrame().setVisible(true));
        menuDoctors.addActionListener(e -> new DoctorManagementFrame().setVisible(true));
    }
}
