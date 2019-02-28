package com.infy.student.Exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex,WebRequest wr){
		
		ExceptionResponse response=new ExceptionResponse(new Date(),ex.getMessage(), wr.getDescription(false));
		
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public final ResponseEntity<Object> studentNotFoundExceptions(StudentNotFoundException ex,WebRequest wr){
		
		ExceptionResponse response=new ExceptionResponse(new Date(),ex.getMessage(), wr.getDescription(false));
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse response=new ExceptionResponse(new Date(),"Validation Failed", 
				ex.getBindingResult().toString());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
