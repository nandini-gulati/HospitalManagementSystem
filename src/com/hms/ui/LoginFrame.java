package com.hms.ui;

import com.hms.dao.UserDao;
import com.hms.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Hospital Management System - Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("HMS Login");
        lblTitle.setBounds(160, 10, 100, 25);
        add(lblTitle);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 60, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 60, 180, 25);
        add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 100, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 180, 25);
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener((ActionEvent e) -> doLogin());
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Basic input validation (error handling)
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username and password are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            UserDao dao = new UserDao();
            User user = dao.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this,
                        "Login successful! Role: " + user.getRole());
                new DashboardFrame(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            // Catch any unexpected DB or runtime error and show a user-friendly message
            JOptionPane.showMessageDialog(this,
                    "An error occurred while logging in:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // for debugging in console
        }
    }
}
