package com.ku.kuhamsappointmentservice.controller;

import com.ku.kuhamsappointmentservice.dto.request.AppointmentDTO;
import com.ku.kuhamsappointmentservice.entity.Appointment;
import com.ku.kuhamsappointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Endpoint to book a new appointment
    @PostMapping("/book")
    public Appointment bookAppointment(@RequestParam Long patientId,
                                       @RequestParam Long doctorId,
                                       @RequestParam String dateTime) {
        LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTime);
        return appointmentService.bookAppointment(patientId, doctorId, appointmentDateTime);
    }

    // Endpoint to get all appointments for a patient
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsForPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsForPatient(patientId);
    }

    // Endpoint to get all appointments for a doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsForDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsForDoctor(doctorId);
    }

    // Endpoint to reschedule an appointment
    @PostMapping("/reschedule")
    public Appointment rescheduleAppointment(@RequestParam Long appointmentId,
                                             @RequestParam String newDateTime) {
        LocalDateTime newAppointmentDateTime = LocalDateTime.parse(newDateTime);
        return appointmentService.rescheduleAppointment(appointmentId, newAppointmentDateTime);
    }

    // Endpoint to cancel an appointment
    @PostMapping("/cancel")
    public void cancelAppointment(@RequestParam Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }
}
