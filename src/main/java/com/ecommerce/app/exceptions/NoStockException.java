package com.ecommerce.app.exceptions;

public class NoStockException extends EcommerceException {

	private static final long serialVersionUID = 1L;
	
	public final Integer status = 404;
	
	public final String errorMessage = "Out of stock product available!";
}
