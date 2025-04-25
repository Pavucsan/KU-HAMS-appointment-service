package com.ku.kuhamsappointmentservice.controller;

import com.ku.kuhamsappointmentservice.dto.request.PatientCreateRequest;
import com.ku.kuhamsappointmentservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/api/patients")
    public ResponseEntity<Void> createPatient(@RequestBody PatientCreateRequest r) {
        patientService.createPatient(
                r.userId(), r.username(),
                r.dateOfBirth(), r.gender(),
                r.phoneNumber(), r.email()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
