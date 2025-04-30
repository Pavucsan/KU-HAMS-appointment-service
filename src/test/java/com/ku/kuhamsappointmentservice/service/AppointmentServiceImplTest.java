package com.ku.kuhamsappointmentservice.service;

import com.ku.kuhamsappointmentservice.entity.Appointment;
import com.ku.kuhamsappointmentservice.entity.Doctor;
import com.ku.kuhamsappointmentservice.entity.Patient;
import com.ku.kuhamsappointmentservice.repository.AppointmentRepository;
import com.ku.kuhamsappointmentservice.repository.DoctorRepository;
import com.ku.kuhamsappointmentservice.repository.PatientRepository;
import com.ku.kuhamsappointmentservice.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void testBookAppointment_Success() {
        Long patientId = 1L;
        Long doctorId = 2L;
        LocalDateTime appointmentDateTime = LocalDateTime.of(2025, 5, 1, 10, 0);

        Patient mockPatient = new Patient();
        mockPatient.setId(patientId);

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(doctorId);

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(100L);


        when(patientRepository.findById(patientId)).thenReturn(Optional.of(mockPatient));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(mockDoctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        // Execute
        Appointment result = appointmentService.bookAppointment(patientId, doctorId, appointmentDateTime);

        // Verify
        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testBookAppointment_PatientOrDoctorNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                appointmentService.bookAppointment(1L, 2L, LocalDateTime.now())
        );

        assertEquals("Patient or Doctor not found", exception.getMessage());
    }
}
