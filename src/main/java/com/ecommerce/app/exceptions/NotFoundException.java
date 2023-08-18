package com.ecommerce.app.exceptions;

public class NotFoundException extends EcommerceException {

	private static final long serialVersionUID = 1L;

	public final Integer status = 404;
	
	public String errorMessage;
	
	public NotFoundException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public NotFoundException() {
	}
}
