package com.goweb.exception;

/**
 * 基本异常
 * 
 * @author yinsheng
 * 
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 6685861014769477817L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	
}
