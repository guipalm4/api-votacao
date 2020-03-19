package com.guiPalma.apivotacao.exceptions;


public class ServiceErrorException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ServiceErrorException(String msg) {
		super(msg);
	}
	
	public ServiceErrorException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
