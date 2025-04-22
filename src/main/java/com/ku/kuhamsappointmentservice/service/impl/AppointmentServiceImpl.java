package com.ku.kuhamsappointmentservice.service.impl;

import com.ku.kuhamsappointmentservice.dto.request.AppointmentDTO;
import com.ku.kuhamsappointmentservice.entity.Appointment;
import com.ku.kuhamsappointmentservice.entity.Doctor;
import com.ku.kuhamsappointmentservice.entity.Patient;
import com.ku.kuhamsappointmentservice.repository.AppointmentRepository;
import com.ku.kuhamsappointmentservice.repository.DoctorRepository;
import com.ku.kuhamsappointmentservice.repository.PatientRepository;
import com.ku.kuhamsappointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AppointmentDTO mapToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getCreatedAt(),
                appointment.getStatus(),
                "",
               ""
        );
    }

    // Book a new appointment
    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (patient.isPresent() && doctor.isPresent()) {
            Appointment appointment = new Appointment();
            appointment.setPatient(patient.get());
            appointment.setDoctor(doctor.get());
            appointment.setAppointmentDate(appointmentDateTime.toLocalDate());
            appointment.setAppointmentTime(appointmentDateTime.toLocalTime());
            appointment.setStatus("Scheduled");
            appointment.setCreatedAt(LocalDateTime.now());
            appointment.setUpdatedAt(LocalDateTime.now());

            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Patient or Doctor not found");
        }
    }

    // Get all appointments for a patient
    @Override
    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Get all appointments for a doctor
    @Override
    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Reschedule an appointment
    @Override
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newAppointmentDateTime) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setAppointmentDate(newAppointmentDateTime.toLocalDate());
            appointment.setAppointmentTime(newAppointmentDateTime.toLocalTime());
            appointment.setUpdatedAt(LocalDateTime.now());

            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }

    // Cancel an appointment
    @Override
    public void cancelAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setStatus("Canceled");
            appointment.setUpdatedAt(LocalDateTime.now());

            appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }
}
