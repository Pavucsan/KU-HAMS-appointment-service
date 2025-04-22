package com.ku.kuhamsappointmentservice.service;

import com.ku.kuhamsappointmentservice.dto.request.AppointmentDTO;
import com.ku.kuhamsappointmentservice.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> getAllAppointments();
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime appointmentDateTime);
    public List<Appointment> getAppointmentsForPatient(Long patientId);
    public List<Appointment> getAppointmentsForDoctor(Long doctorId);
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newAppointmentDateTime);
    public void cancelAppointment(Long appointmentId);
}
