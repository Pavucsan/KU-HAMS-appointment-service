package com.ku.kuhamsappointmentservice.service.impl;

import com.ku.kuhamsappointmentservice.entity.Patient;
import com.ku.kuhamsappointmentservice.entity.User;
import com.ku.kuhamsappointmentservice.repository.PatientRepository;
import com.ku.kuhamsappointmentservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    public void createPatient(Long userId, String username, String dob, String gender, String phone, String email) {
        User u = new User(); u.setId(userId);
        Patient p = new Patient();
        p.setUser(u);
        p.setFullName(username);
        p.setDateOfBirth(dob);
        p.setGender(gender);
        p.setPhoneNumber(phone);
        p.setEmail(email);
        patientRepository.save(p);
    }
}
