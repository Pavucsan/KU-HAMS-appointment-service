package com.ku.kuhamsappointmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentBookingRequest {
    private long patientId;
    private long doctorId;
    private String dateTime;
}