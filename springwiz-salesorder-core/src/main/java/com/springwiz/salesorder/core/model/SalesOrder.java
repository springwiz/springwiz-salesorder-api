package com.springwiz.salesorder.core.model;

import java.util.Date;

/**
 * The Class SalesOrderDTO.
 */
public class SalesOrder {
	
	/** The id. */
	private String id = "";
	
	/** The farm id. */
	private String farmId = "";
	
	/** The duration. */
	private int duration = 0;
	
	/** The start date time. */
	private Date startDateTime;
	
	/** The status. */
	private Status status;
		
	/**
	 * The Enum Status.
	 */
	public enum Status {
		
		/** The requested. */
		REQUESTED("Requested"),

		/** The inprogress. */
		INPROGRESS("InProgress"),
		
		/** The delivered. */
		DELIVERED("Delivered"),

		/** The cancelled. */
		CANCELLED("Cancelled");

		/** The str value. */
		private String strValue;

		/**
		 * Instantiates a new status.
		 *
		 * @param strValue the str value
		 */
		private Status(String strValue) {
			this.strValue = strValue;
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return strValue;
		}
	} 

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the farm id.
	 *
	 * @return the farmId
	 */
	public String getFarmId() {
		return farmId;
	}

	/**
	 * Sets the farm id.
	 *
	 * @param farmId the farmId to set
	 */
	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets the start date time.
	 *
	 * @return the startDateTime
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Sets the start date time.
	 *
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
