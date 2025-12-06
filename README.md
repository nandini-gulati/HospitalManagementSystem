# 🏥 Hospital Management System (Java, Swing, JDBC, MySQL)

A full-fledged **Hospital Management System** built using **Java**, **Swing GUI**, **JDBC**, and complete **OOP concepts**.  
The project includes secure login, patient management, doctor management, error handling, and a menu-driven dashboard.

---

## 🚀 Features

### 🔐 User Authentication
- Login with credentials stored in MySQL  
- Role supported: **ADMIN**

### 📋 Admin Dashboard
- Menu-driven GUI
- Manage Patients
- Manage Doctors
- Logout

### 👨‍⚕️ Patient Management
- Add patient  
- Update patient  
- Delete patient  
- Search by name  
- View all patients in JTable  
- Full validation & error handling  

### 🩺 Doctor Management
- Add doctor  
- Update doctor  
- Delete doctor  
- Search by name  
- View all doctors in JTable  
- Inherits from Person class (OOP)

---

## 🧱 OOP Concepts Implemented

| OOP Concept      | Where Used |
|------------------|------------|
| Abstraction      | `Person` abstract class |
| Inheritance      | `Patient` and `Doctor` extend `Person` |
| Polymorphism     | Overridden `displayInfo()` |
| Encapsulation    | Private fields + getters/setters |
| Interface        | `CrudRepository<T>` |
| Exception Handling | `InvalidDataException`, DB error handling |
| Packages         | model, dao, ui, exception, main |

---

## 🗄️ Database Schema (MySQL)

```sql
CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(20)
);

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin', 'ADMIN');

CREATE TABLE patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    phone VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(200),
    age INT,
    gender VARCHAR(10),
    blood_group VARCHAR(10)
);

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    phone VARCHAR(15),
    email VARCHAR(100),
    specialization VARCHAR(100),
    experience_years INT,
    consultation_fee INT
);
```

---

## 📁 Folder Structure

```
HospitalManagementSystem
│
├── lib
│   └── mysql-connector-j-9.5.0.jar
│
└── src/com/hms
    ├── main
    │   ├── Main.java
    │   └── DatabaseInitializer.java
    │
    ├── dao
    │   ├── DbConnection.java
    │   ├── UserDao.java
    │   ├── PatientDao.java
    │   └── DoctorDao.java
    │
    ├── model
    │   ├── Person.java
    │   ├── Patient.java
    │   └── Doctor.java
    │
    ├── ui
    │   ├── LoginFrame.java
    │   ├── DashboardFrame.java
    │   ├── PatientManagementFrame.java
    │   └── DoctorManagementFrame.java
    │
    └── exception
        └── InvalidDataException.java
```

---

## 🛠️ Technologies Used
- Java (Core + OOP)
- Swing (GUI)
- JDBC
- MySQL
- VS Code / IntelliJ
- Git & GitHub

---

## ▶️ How to Run

### 1️⃣ Add MySQL Connector JAR to:
```
/lib/mysql-connector-j-9.5.0.jar
```

### 2️⃣ Initialize Database
```
java -cp "lib/mysql-connector-j-9.5.0.jar:src" com.hms.main.DatabaseInitializer
```

### 3️⃣ Run Application
```
java -cp "lib/mysql-connector-j-9.5.0.jar:src" com.hms.main.Main
```

---

## 🧪 Default Login
| Username | Password | Role  |
|---------|----------|-------|
| admin   | admin    | ADMIN |

---

## 💡 Future Enhancements
- Appointment Scheduling  
- Billing System  
- Staff Dashboard  
- PDF Report Generation  
- Dark Mode  

---

## 👤 Author
**Nandini Gulati**  
B.Tech CSE (Full Stack Development), UPES  

---

## ⭐ Support
If you found this project useful, please ⭐ star the repository!
