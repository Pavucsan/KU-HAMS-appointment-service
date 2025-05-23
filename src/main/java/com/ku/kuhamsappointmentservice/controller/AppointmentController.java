package com.ku.kuhamsappointmentservice.controller;

import com.ku.kuhamsappointmentservice.dto.request.AppointmentBookingRequest;
import com.ku.kuhamsappointmentservice.dto.request.AppointmentDTO;
import com.ku.kuhamsappointmentservice.dto.response.ResponseWrapper;
import com.ku.kuhamsappointmentservice.entity.Appointment;
import com.ku.kuhamsappointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentBookingRequest request) {
        try {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(
                    request.getDateTime(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            Appointment appointment = appointmentService.bookAppointment(
                    request.getPatientId(),
                    request.getDoctorId(),
                    appointmentDateTime
            );

            // Return the response wrapped with status and message
            ResponseWrapper successResponse = new ResponseWrapper("success", "Appointment booked successfully", appointment);
            return ResponseEntity.ok(successResponse);

        } catch (DateTimeParseException e) {
            ResponseWrapper errorResponse = new ResponseWrapper("error", "Invalid date format. Please use ISO format: yyyy-MM-dd'T'HH:mm:ss");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception ex) {
            ResponseWrapper errorResponse = new ResponseWrapper("error", "Booking failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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
