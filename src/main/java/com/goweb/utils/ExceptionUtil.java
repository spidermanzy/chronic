package com.goweb.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;

import com.goweb.exception.ServiceException;

/**
 * 异常的工具类.
 * 
 * 
 * 
 */
public class ExceptionUtil {

	
	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 获取异常和低层异常的信息
	 */
	public static String getErrorMessageWithNestedException(Throwable ex) {
		Throwable nestedException = ex.getCause();
		return new StringBuilder().append(ex.getMessage()).append(" nested exception is ")
				.append(nestedException.getClass().getName()).append(":").append(nestedException.getMessage())
				.toString();
	}

	/**
	 * 获取最低层异常
	 */
	public static Throwable getRootCause(Throwable ex) {
		Throwable cause;
		while ((cause = ex.getCause()) != null) {
			ex = cause;
		}
		return ex;
	}
	
	
	
}
