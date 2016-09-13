package com.nichesoftware.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par défaut
	 */
	public BadRequestException() {
		super();
	}
	
	/**
	 * Constructeur
	 * @param e
	 */
	public BadRequestException(Exception e) {
		super(e);
	}
	
	/**
	 * Constructeur
	 * @param message
	 */
	public BadRequestException(String message) {
		super(message);
	}

	/**
	 * Constructeur
	 * @param e
	 * @param message
	 */
	public BadRequestException(String message, Exception e) {
		super(message, e);
	}

}
