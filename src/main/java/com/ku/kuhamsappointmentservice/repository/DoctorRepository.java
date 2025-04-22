package com.ku.kuhamsappointmentservice.repository;

import com.ku.kuhamsappointmentservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT DISTINCT d.specialization FROM Doctor d")
    List<String> findAllDistinctSpecializations();

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.fullName) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(d.specialization) LIKE LOWER(CONCAT('%', :speciality, '%'))")
    List<Doctor> searchDoctors(@Param("name") String name, @Param("speciality") String speciality);
}
