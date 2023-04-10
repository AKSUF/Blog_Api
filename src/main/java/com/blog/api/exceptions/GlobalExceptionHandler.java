package com.blog.api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.api.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
		String message = exception.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse> (apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exception){
		Map<String, String> resp = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error)-> {
			String field =((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(field, message);
			
		});
		return new ResponseEntity<Map<String, String>> (resp, HttpStatus.NOT_FOUND);

	}
	
	

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse>handlerApiException(ApiException exception){
		String message = exception.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse> (apiResponse, HttpStatus.BAD_REQUEST);
	}
}
