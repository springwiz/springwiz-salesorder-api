package com.springwiz.salesorder.core.dao;

import java.util.Date;
import java.util.List;

import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;

/**
 * The Interface SalesOrderRepository.
 */
public interface SalesOrderRepository {
	
	/**
	 * Find sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	SalesOrder findSalesOrder(String salesOrderId) throws PersistenceException;
	
	/**
	 * Find sales order by farm id.
	 *
	 * @param farmId the farm id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	List<SalesOrder> findSalesOrderByFarmId(String farmId) throws PersistenceException;
	
	/**
	 * Creates the sales order.
	 *
	 * @param order the order
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	SalesOrder createSalesOrder(SalesOrder order) throws PersistenceException;
	
	/**
	 * Cancel sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	SalesOrder cancelSalesOrder(String salesOrderId) throws PersistenceException;
	
	/**
	 * Find orders by start date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	List<SalesOrder> findOrdersByStartDateTime(Date currentDateTime) throws PersistenceException;
	
	/**
	 * Find orders by end date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	List<SalesOrder> findOrdersByEndDateTime(Date currentDateTime) throws PersistenceException;
}
