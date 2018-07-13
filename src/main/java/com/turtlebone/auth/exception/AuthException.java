package com.turtlebone.auth.exception;


import lombok.Data;

@Data
public class AuthException extends Exception {
	protected String errorMesage;
	
	public AuthException(String errorMessage){
		this.errorMesage = errorMessage;
	}
}
