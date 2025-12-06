package com.hms.model;

public class Patient extends Person {
    private String address;
    private int age;
    private String gender;
    private String bloodGroup;

    public Patient() {}

    public Patient(int id, String name, String phone, String email,
                   String address, int age, String gender, String bloodGroup) {
        super(id, name, phone, email);
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    @Override
    public void displayInfo() {
        System.out.println("Patient: " + name + " (" + age + " yrs), Phone: " + phone);
    }
}
