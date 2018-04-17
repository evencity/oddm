package com.apical.oddm.core.exception;

public class OddmRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OddmRuntimeException() {
		super();
	}

	public OddmRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public OddmRuntimeException(String message) {
		super(message);
	}

	public OddmRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
