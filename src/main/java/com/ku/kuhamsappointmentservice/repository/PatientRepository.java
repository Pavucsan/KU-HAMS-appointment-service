package com.ku.kuhamsappointmentservice.repository;

import com.ku.kuhamsappointmentservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
