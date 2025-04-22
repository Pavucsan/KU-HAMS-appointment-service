package com.ku.kuhamsappointmentservice.repository;

import com.ku.kuhamsappointmentservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
