package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {

	private HttpStatus status;
	private String message;
	
	
	public BlogApiException(HttpStatus ststus, String message) {
		super();
		this.status = ststus;
		this.message = message;
	}


	public BlogApiException(String message, HttpStatus ststus, String message1) {
		super(message);
		this.status = ststus;
		message = message1;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	@Override
	public String getMessage() {
		
		return message;
	}
	
	
	
	
	
	

}
