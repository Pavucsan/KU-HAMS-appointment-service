package com.ku.kuhamsappointmentservice.controller;

import com.ku.kuhamsappointmentservice.dto.DoctorDto;
import com.ku.kuhamsappointmentservice.dto.request.DoctorCreateRequest;
import com.ku.kuhamsappointmentservice.entity.Doctor;
import com.ku.kuhamsappointmentservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/specializations")
    public ResponseEntity<List<String>> getSpecializations() {
        return ResponseEntity.ok(doctorService.getAllSpecializations());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorDto>> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String speciality) {
        return ResponseEntity.ok(doctorService.searchDoctors(name, speciality));
    }

    @GetMapping("/key/search")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam("keyword") String keyword) {
        List<Doctor> result = doctorService.searchDoctors(keyword);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/doctors")
    public ResponseEntity<Void> createDoctor(@RequestBody DoctorCreateRequest r) {
        doctorService.createDoctor(
                r.userId(), r.username(), r.specialization(),
                r.phoneNumber(), r.email(), r.availabilityStart(), r.availabilityEnd()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }
}
