package com.ahold.ctp.sandbox.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.util.List

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val response: ApiErrorResponse = ApiErrorResponse(
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                ex.bindingResult
                        .fieldErrors
                        .stream()
                        .map { error: FieldError -> error.field + ": " + error.defaultMessage }.toList(),
                LocalDateTime.now()
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidDeliveryRequestException::class)
    fun handleInvalidDeliveryRequestException(ex: InvalidDeliveryRequestException): ResponseEntity<ApiErrorResponse> {
        val response: ApiErrorResponse =
                ApiErrorResponse(
                "Bad Request",
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.message),
                LocalDateTime.now()

        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ApiErrorResponse> {
        val response: ApiErrorResponse = ApiErrorResponse(
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(ex.message),
                LocalDateTime.now()
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}