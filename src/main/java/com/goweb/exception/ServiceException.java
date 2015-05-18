package com.goweb.exception;

/**
 * 
 * Service 异常类
 * 
 * 
 * 
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = 6685861014769477817L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	
}
