package com.bsquare.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bsquare.BSquareJobPortalApplication;
import com.bsquare.exception.JobPortalException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice // This annotation is used to handle exceptions globally for all controllers
public class ExceptionControllerAdvice {

	private final BSquareJobPortalApplication BSquareJobPortalApplication;
	
	@Autowired
	private Environment environment; // Used to read properties from application.properties
	
	// Constructor Injection of main application class
	ExceptionControllerAdvice(BSquareJobPortalApplication BSquareJobPortalApplication) {
		this.BSquareJobPortalApplication = BSquareJobPortalApplication;
	}
	
	// This class controls and handles all exceptions across the project uniformly

	// General exception handler for uncaught exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalException(Exception exception) {
		// Creating error response object with message, status code, and timestamp
		ErrorInfo error = new ErrorInfo(
			exception.getMessage(),
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			LocalDateTime.now()
		);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Custom exception handler for JobPortalException (example: duplicate email, ID not found, etc.)
	@ExceptionHandler(JobPortalException.class)
	public ResponseEntity<ErrorInfo> generalException(JobPortalException exception) {
		// Fetching error message from application.properties using exception message as key
		String msg = environment.getProperty(exception.getMessage());
		
		// Creating error response
		ErrorInfo error = new ErrorInfo(
			msg,
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			LocalDateTime.now()
		);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Handler for validation errors (both field-level and parameter-level validations)
	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
		String msg = "";

		// If validation error comes from DTO fields (like @Valid request body)
		if (exception instanceof MethodArgumentNotValidException manvException) {
			msg = manvException.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		}
		// If validation error comes from path variables or request parameters
		else if (exception instanceof ConstraintViolationException cvException) {
			msg = cvException.getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(", "));
		}

		// Creating error response for validation errors
		ErrorInfo error = new ErrorInfo(
			msg,
			HttpStatus.BAD_REQUEST.value(),
			LocalDateTime.now()
		);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
