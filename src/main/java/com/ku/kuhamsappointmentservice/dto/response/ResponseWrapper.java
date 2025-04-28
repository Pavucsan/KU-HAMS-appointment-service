package com.ku.kuhamsappointmentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseWrapper {
    private String status;
    private String message;
    private Object data;
    public ResponseWrapper(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
