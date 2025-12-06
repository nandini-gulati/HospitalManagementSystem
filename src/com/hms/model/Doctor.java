package com.hms.model;

public class Doctor extends Person {
    private String specialization;
    private int experienceYears;
    private int consultationFee;

    public Doctor() {}

    public Doctor(int id, String name, String phone, String email,
                  String specialization, int experienceYears, int consultationFee) {
        super(id, name, phone, email);
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public int getConsultationFee() { return consultationFee; }
    public void setConsultationFee(int consultationFee) { this.consultationFee = consultationFee; }

    @Override
    public void displayInfo() {
        System.out.println("Doctor: " + name + " (" + specialization + "), Exp: "
                + experienceYears + " years");
    }
}
