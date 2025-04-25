package com.ku.kuhamsappointmentservice.service.impl;

import com.ku.kuhamsappointmentservice.dto.DoctorDto;
import com.ku.kuhamsappointmentservice.entity.Doctor;
import com.ku.kuhamsappointmentservice.entity.User;
import com.ku.kuhamsappointmentservice.repository.DoctorRepository;
import com.ku.kuhamsappointmentservice.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public List<String> getAllSpecializations() {
        return doctorRepository.findAllDistinctSpecializations();
    }

    @Override
    public List<DoctorDto> searchDoctors(String name, String speciality) {
        List<Doctor> doctors = doctorRepository.searchDoctors(
                name != null ? name : "", speciality != null ? speciality : "");
        return doctors.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.searchByKeyword(keyword);
    }

    private DoctorDto mapToDto(Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFullName(), doctor.getSpecialization());
    }

    public void createDoctor(Long userId, String username, String speciality, String phone, String email, String start, String end) {
        User u = new User(); u.setId(userId);
        Doctor d = new Doctor();
        d.setUser(u);
        d.setFullName(username);
        d.setSpecialization(speciality);
        d.setPhoneNumber(phone);
        d.setEmail(email);
        d.setAvailabilityStart(start);
        d.setAvailabilityEnd(end);
        doctorRepository.save(d);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + id));
    }
}