package com.tmsht.custom.exceptionhandlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pmt.custom.exceptions.InternalServerException;
import com.pmt.custom.exceptions.RecordNotFoundException;
import com.pmt.util.response.ApiFieldError;
import com.tmsht.util.response.ApiError;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String DESERIALIZATION_FAILURE = "Invalid input data format.";
	private static final String VALIDATION_FAILURE = "Validation failed.";
	private static final String RECORD_NOT_FOUND = "Record not found.";
	private static final String FIELD_MISSING = "Field is missing.";
	private static final String INTERNAL_SERVER_ERROR = "Internal server error.";

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		ApiError apiError = new ApiError(LocalDateTime.now(), RECORD_NOT_FOUND, details);
		return new ResponseEntity<Object>(apiError, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(InternalServerException.class)
	public final ResponseEntity<Object> handleInternalServerException(InternalServerException ex, WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		ApiError apiError = new ApiError(LocalDateTime.now(), INTERNAL_SERVER_ERROR, details);
		return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiFieldError apiFieldError = new ApiFieldError();
		List<ApiFieldError.Detail> details = new ArrayList<ApiFieldError.Detail>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			ApiFieldError.Detail detail = apiFieldError.new Detail(
					error.getRejectedValue() == null ? FIELD_MISSING : error.getRejectedValue().toString(),
					error.getDefaultMessage(), error.getField());
			details.add(detail);
		}
		apiFieldError.setDetails(details);
		apiFieldError.setMessage(VALIDATION_FAILURE);
		apiFieldError.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(apiFieldError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		ApiFieldError apiFieldError = new ApiFieldError();
		List<ApiFieldError.Detail> details = ex.getConstraintViolations().stream().map(constraintViolation -> {
			ApiFieldError.Detail detail = apiFieldError.new Detail(constraintViolation.getInvalidValue().toString(),
					constraintViolation.getMessage(),
					StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), false)
							.map(Path.Node::getName).reduce((first, second) -> second)
							.orElseGet(() -> constraintViolation.getPropertyPath().toString()));
			return detail;
		}).collect(Collectors.toList());
		apiFieldError.setDetails(details);
		apiFieldError.setMessage(VALIDATION_FAILURE);
		apiFieldError.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<Object>(apiFieldError, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ApiError error = new ApiError(LocalDateTime.now(), DESERIALIZATION_FAILURE, details);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);

	}

}
