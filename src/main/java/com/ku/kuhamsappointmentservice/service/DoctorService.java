package com.ku.kuhamsappointmentservice.service;

import com.ku.kuhamsappointmentservice.dto.DoctorDto;
import com.ku.kuhamsappointmentservice.entity.Doctor;

import java.util.List;

public interface DoctorService {
    public List<String> getAllSpecializations();

    public List<DoctorDto> searchDoctors(String name, String speciality);
    public List<Doctor> searchDoctors(String keyword);
    public void createDoctor(Long userId, String username, String speciality, String phone, String email, String start, String end);
    public Doctor getDoctorById(Long id);
}
