package com.ku.kuhamsappointmentservice.service;

import com.ku.kuhamsappointmentservice.entity.Doctor;
import com.ku.kuhamsappointmentservice.entity.User;
import com.ku.kuhamsappointmentservice.repository.DoctorRepository;
import com.ku.kuhamsappointmentservice.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void searchDoctorsReturnsDoctorsMatchingKeyword() {
        String keyword = "cardio";
        User user = new User();
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setUser(user);
        doctor1.setFullName("Dr. John Smith");
        doctor1.setSpecialization("Cardiology");
        doctor1.setPhoneNumber("1234567890");
        doctor1.setEmail("john@example.com");
        doctor1.setAvailabilityStart("09:00");
        doctor1.setAvailabilityEnd("17:00");

        List<Doctor> mockDoctors = List.of(doctor1);
        when(doctorRepository.searchByKeyword(keyword)).thenReturn(mockDoctors);

        List<Doctor> result = doctorService.searchDoctors(keyword);

        assertEquals(1, result.size());
        assertEquals("Dr. John Smith", result.get(0).getFullName());
        verify(doctorRepository).searchByKeyword(keyword);
    }

    @Test
    void searchDoctorsWithNoMatchReturnsEmptyList() {
        String keyword = "unknown";
        when(doctorRepository.searchByKeyword(keyword)).thenReturn(Collections.emptyList());

        List<Doctor> result = doctorService.searchDoctors(keyword);

        assertTrue(result.isEmpty());
        verify(doctorRepository).searchByKeyword(keyword);
    }
}