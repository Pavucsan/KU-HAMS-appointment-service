package com.ku.kuhamsappointmentservice.service;

import com.ku.kuhamsappointmentservice.dto.DoctorDto;

import java.util.List;

public interface DoctorService {
    public List<String> getAllSpecializations();

    public List<DoctorDto> searchDoctors(String name, String speciality);
}
