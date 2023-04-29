package com.grit.learning.exception;

public class BusinessServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String errorMsg;

	private final Exception ex;

	private final boolean isBusinessException;

	private final String errorCode;

	public BusinessServiceException() {
		this.ex = null;
		this.errorMsg = null;
		this.errorCode = null;
		this.isBusinessException = true;
	}

	public BusinessServiceException(String errorMsg) {
		super(errorMsg);
		this.ex = null;
		this.errorMsg = errorMsg;
		this.errorCode = null;
		this.isBusinessException = true;
	}

	public BusinessServiceException(String errorMsg, String errorCode) {
		super(errorMsg);
		this.ex = null;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
		this.isBusinessException = true;
	}

	public BusinessServiceException(String errorMsg, String errorCode, Exception ex) {
		super(errorMsg);
		this.ex = ex;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
		this.isBusinessException = ex instanceof BusinessServiceException;
	}

	public BusinessServiceException(Exception ex) {
		super(ex);
		this.errorMsg = null;
		this.ex = ex;
		this.errorCode = null;
		this.isBusinessException = ex instanceof BusinessServiceException;
	}

	public static BusinessServiceException exception(Exception ex) {
		BusinessServiceException exception = null;
		if (ex instanceof BusinessServiceException) {
			exception = (BusinessServiceException) ex;
		} else {
			exception = new BusinessServiceException(ex);
		}
		return exception;
	}

	public BusinessServiceException(String errorMsg, Exception ex) {
		super(errorMsg, ex);
		this.errorMsg = errorMsg;
		this.ex = ex;
		this.errorCode = null;
		this.isBusinessException = ex instanceof BusinessServiceException;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public Exception getEx() {
		return ex;
	}

	public boolean isBusinessException() {
		return isBusinessException;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
