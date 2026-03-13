package com.abhishekvermaa10.exception;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Map<String, String> CONSTRAINT_MESSAGES = Map.of(
            "UKev54t0ij3mffa65v6m6umjpdh", "Email already exists",
            "UK_mobile_number", "Mobile number already exists"
    );

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

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        ErrorDTO errorDTO = ErrorDTO.builder().
                message(httpRequestMethodNotSupportedException.getMessage())
                .error(HttpStatus.METHOD_NOT_ALLOWED)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        Throwable cause = ex.getRootCause();

        if (cause instanceof org.hibernate.exception.ConstraintViolationException constraintEx) {

            String constraintName = constraintEx.getConstraintName();

            String message = CONSTRAINT_MESSAGES.getOrDefault(
                    constraintName,
                    "Database constraint violation"
            );

            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.badRequest().body("Database error");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleEnumException(HttpMessageNotReadableException exception) {
        String errorDetails = "";
        if (exception.getCause() instanceof InvalidFormatException invalidFormatException && Objects.nonNull(invalidFormatException.getTargetType())
                && invalidFormatException.getTargetType().isEnum()) {
            errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s ", invalidFormatException.getValue(), invalidFormatException.getPath().get(invalidFormatException.getPath().size() - 1).getFieldName(), Arrays.toString(invalidFormatException.getTargetType().getEnumConstants()));
        }
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorDetails)
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorDTO>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ErrorDTO> errorDTOList = exception.getConstraintViolations().stream()
                .map(error -> ErrorDTO.builder()
                        .message(error.getMessage())
                        .error(HttpStatus.BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTOList);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<ErrorDTO> errorDTOList = errorList.stream().map(error -> ErrorDTO.builder()
                        .message(error.getDefaultMessage())
                        .error(HttpStatus.BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTOList);
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
