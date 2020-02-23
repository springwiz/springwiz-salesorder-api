package com.springwiz.salesorder.core.status;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * The Class SalesOrderStatus.
 *
 * @author sumitnarayan
 */
public class SalesOrderStatus implements Delayed {
	/** The id. */
	private String id = "";
	
	/** The status. */
	private String status = "";
	
	/** The order status time. */
	private long orderStatusTime;
	
	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Delayed o) {
		return (int) (orderStatusTime - ((SalesOrderStatus)o).orderStatusTime);
	}

	/**
	 * Gets the delay.
	 *
	 * @param unit the unit
	 * @return the delay
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		long diff = orderStatusTime - System.currentTimeMillis();
	    return unit.convert(diff, TimeUnit.MILLISECONDS);
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
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the order status time.
	 *
	 * @return the order status time
	 */
	public long getOrderStatusTime() {
		return orderStatusTime;
	}

	/**
	 * Sets the order status time.
	 *
	 * @param orderStatusTime the new order status time
	 */
	public void setOrderStatusTime(long orderStatusTime) {
		this.orderStatusTime = orderStatusTime;
	}
}
