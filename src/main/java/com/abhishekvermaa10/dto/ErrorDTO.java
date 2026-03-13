package com.abhishekvermaa10.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDTO {

    private String message;
    private HttpStatus error;
    private Integer status;
    private LocalDateTime timestamp;

}
