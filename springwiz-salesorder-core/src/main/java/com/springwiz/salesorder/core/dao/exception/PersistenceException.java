package com.springwiz.salesorder.core.dao.exception;

/**
 * The Class PersistenceException.
 */
public class PersistenceException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new persistence exception.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public PersistenceException(String msg, Exception e) {
		super(msg, e);
	}
}
