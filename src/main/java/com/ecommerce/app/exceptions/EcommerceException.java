package com.ecommerce.app.exceptions;

import org.springframework.http.HttpStatusCode;

public class EcommerceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpStatusCode status;
	
	public String errorMessage;
	
	public EcommerceException() {
	}

	public EcommerceException(HttpStatusCode status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}
}
