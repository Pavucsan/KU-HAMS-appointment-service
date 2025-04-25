package com.ku.kuhamsappointmentservice.dto.request;

public record PatientCreateRequest(
        Long userId,
        String username,
        String dateOfBirth,
        String gender,
        String phoneNumber,
        String email
) {}