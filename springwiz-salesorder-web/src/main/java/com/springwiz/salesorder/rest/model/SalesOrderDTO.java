package com.springwiz.salesorder.rest.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springwiz.salesorder.core.model.SalesOrder.Status;

/**
 * The Class SalesOrderDTO.
 */
public class SalesOrderDTO {
	/** The farm id. */
	private String farmId = "";
	
	/** The duration. */
	private int duration = 0;
	
	/** The start date time. */
	private Date startDateTime;
	
	/** The status. */
	private Status status;

	/**
	 * Gets the farm id.
	 *
	 * @return the farmId
	 */
	@JsonProperty
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
	@JsonProperty
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
	@JsonProperty
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
	@JsonProperty
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
