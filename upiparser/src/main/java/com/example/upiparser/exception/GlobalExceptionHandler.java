package com.example.upiparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.upiparser.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler{
	@ExceptionHandler
	 public ResponseEntity<ApiResponse<String>> handleException(Exception ex){
		 ApiResponse<String> response=new ApiResponse<>(
				 false,
				 "Error: "+ex.getMessage(),
				 null
				 );
		 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	 }
}