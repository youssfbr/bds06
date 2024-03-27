package com.github.youssfbr.movieflix.controllers.exceptions;

import com.github.youssfbr.movieflix.services.exceptions.DatabaseException;
import com.github.youssfbr.movieflix.services.exceptions.ForbiddenException;
import com.github.youssfbr.movieflix.services.exceptions.ResourceNotFoundException;
import com.github.youssfbr.movieflix.services.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
	private static final  HttpStatus notFound = HttpStatus.NOT_FOUND;
	private static final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
	private static final HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(notFound.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(notFound).body(err);
	}	
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(badRequest.value());
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(badRequest).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(unprocessableEntity.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(unprocessableEntity).body(err);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
		OAuthCustomError err = new OAuthCustomError("Forbidden", e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException e, HttpServletRequest request) {
		OAuthCustomError err = new OAuthCustomError("Unauthorized", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

}
