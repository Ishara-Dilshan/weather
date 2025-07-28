package com.ishara.weather.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiException(String message, HttpStatus status, LocalDateTime timestamp) {
}
