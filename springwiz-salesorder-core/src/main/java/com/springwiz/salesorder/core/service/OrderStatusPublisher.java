package com.springwiz.salesorder.core.service;

import com.springwiz.salesorder.core.status.SalesOrderStatus;

/**
 * The Interface OrderStatusPublisher.
 */
public interface OrderStatusPublisher {

	/**
	 * Publish order status.
	 *
	 * @param status the status
	 */
	void publishOrderStatus(SalesOrderStatus status);
	
	/**
	 * Gets the order status count.
	 *
	 * @return the order status count
	 */
	int getOrderStatusCount();
}