package com.abhishekvermaa10.exception;

import com.abhishekvermaa10.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleOwnerNotFoundException(OwnerNotFoundException ownerNotFoundException) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(ownerNotFoundException.getMessage())
                .error(HttpStatus.NOT_FOUND)
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    public ResponseEntity<ErrorDTO> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        ErrorDTO errorDTO = ErrorDTO.builder().
                message(httpRequestMethodNotSupportedException.getMessage()).
                error(HttpStatus.METHOD_NOT_ALLOWED).
                status(HttpStatus.METHOD_NOT_ALLOWED.value()).
                timestamp(LocalDateTime.now()).
                build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDTO);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleException(Exception e) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
