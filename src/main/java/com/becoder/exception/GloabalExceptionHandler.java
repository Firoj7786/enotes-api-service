package com.becoder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.becoder.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class GloabalExceptionHandler extends Exception {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("GlobalExceptionHandler :: handleException ::", e.getMessage());
		return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> NullPointerExceptionHandle(Exception e)
	{
		log.error("GlobalExceptionHandler :: handleNullPointerException ::", e.getMessage());
		return CommonUtils.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> ResourceNotFoundException(Exception e)
	{
		return CommonUtils.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		return CommonUtils.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(Exception e)
	{
		return CommonUtils.createErrorResponseMessage(e.getMessage(),HttpStatus.CONFLICT);
	}
}
