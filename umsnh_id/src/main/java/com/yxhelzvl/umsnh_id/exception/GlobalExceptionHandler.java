package com.yxhelzvl.umsnh_id.exception;

import com.yxhelzvl.umsnh_id.dto.ApiResponse;
import com.yxhelzvl.umsnh_id.model.enums.ErrorCode;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        HttpStatus.BAD_REQUEST,
                        "Error de validación",
                        errors
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        System.out.println("Error de integridad de datos: " + ex);;

        String rootCause = ex.getRootCause() != null ?
                ex.getRootCause().getMessage() :
                ex.getMessage();

        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        if (rootCause.contains("Usuarios_UNIQUE_matricula")) {
            errorCode = ErrorCode.DUPLICATE_MATRICULA;
        } else if (rootCause.contains("Usuarios_UNIQUE_email")) {
            errorCode = ErrorCode.DUPLICATE_EMAIL;
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error(
                        HttpStatus.CONFLICT,
                        errorCode.getMessage(),
                        Collections.singletonList(errorCode.getCode())
                )
                );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleDataAccessException(DataAccessException ex) {
        System.out.println("Error de acceso a datos: "+ ex);
        return ResponseEntity.internalServerError().body(
                ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error de base de datos",
                        Collections.singletonList("DATABASE_ERROR")
                )
        );
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.error(
                        HttpStatus.UNAUTHORIZED,
                        ErrorCode.INVALID_CREDENTIALS.getMessage(),
                        Collections.singletonList(ErrorCode.INVALID_CREDENTIALS.getCode())
                )
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleEmptyResult(EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND.getMessage(),
                        Collections.singletonList(ErrorCode.USER_NOT_FOUND.getCode())
                )
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception ex) {
        System.out.println("Error inesperado: "+ ex);

        return ResponseEntity.internalServerError().body(
                ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error interno del servidor",
                        Collections.singletonList("INTERNAL_ERROR")
                )
        );
    }

}