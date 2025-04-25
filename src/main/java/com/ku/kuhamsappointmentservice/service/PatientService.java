package com.ku.kuhamsappointmentservice.service;

public interface PatientService {
    public void createPatient(Long userId, String username, String dob, String gender, String phone, String email);
}
