package com.springwiz.salesorder.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ApiSuccess.
 *
 * @author snarayan
 */
public class ApiSuccess implements ApiResponse {

	/** The code. */
	private String code = "";
	
	/** The status. */
	private ResponseStatus status = ResponseStatus.SUCCESS;
	
	/** The message. */
	private String message = "";
	
	/**
	 * Instantiates a new api success.
	 */
	public ApiSuccess() {
		
	}
	
	/**
	 * Instantiates a new api success.
	 *
	 * @param code the code
	 * @param status the status
	 * @param message the message
	 */
	public ApiSuccess(String code, ResponseStatus status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	@Override
	@JsonProperty
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	@Override
	@JsonProperty
	public ResponseStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	@JsonProperty
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}