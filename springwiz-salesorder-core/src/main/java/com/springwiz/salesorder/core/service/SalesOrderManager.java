/**
 * 
 */
package com.springwiz.salesorder.core.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.springwiz.salesorder.core.dao.SalesOrderRepository;
import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;
import com.springwiz.salesorder.core.model.SalesOrder.Status;
import com.springwiz.salesorder.core.status.SalesOrderStatus;

/**
 * The Class SalesOrderManager.
 *
 * @author sumitnarayan
 */
@Service
public class SalesOrderManager {
	
	/** The sales order repository. */
	@Autowired
	@Qualifier("salesOrderRepositoryImpl")
	private SalesOrderRepository salesOrderRepository;
	
	/** The status publisher. */
	@Autowired
	@Qualifier("consolePublisher")
	private OrderStatusPublisher statusPublisher;
	
	/**
	 * Find sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	public SalesOrder findSalesOrder(String salesOrderId) throws PersistenceException {
		return salesOrderRepository.findSalesOrder(salesOrderId);
	}

	/**
	 * Creates the sales order.
	 *
	 * @param order the order
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	public SalesOrder createSalesOrder(SalesOrder order) throws PersistenceException {
		SalesOrder ord = salesOrderRepository.createSalesOrder(order);
		createAlert(order, Status.REQUESTED);
		createAlert(order, Status.INPROGRESS);
		createAlert(order, Status.DELIVERED);
		return ord;
	}
	
	/**
	 * Creates the alert.
	 *
	 * @param order the order
	 * @param status the status
	 */
	void createAlert(SalesOrder order, SalesOrder.Status status) {
		SalesOrderStatus ordStatus = new SalesOrderStatus();
		ordStatus.setId(order.getId());		
		switch(status) {
		case CANCELLED:
			ordStatus.setStatus("Water order "+ order.getId() +" for farm "+ order.getFarmId()
				+ " was cancelled before delivery");
			ordStatus.setOrderStatusTime(System.currentTimeMillis() + 100);
		case REQUESTED:
			ordStatus.setStatus("New water order "+ order.getId() +" for farm "+ order.getFarmId()
				+ " created");
			ordStatus.setOrderStatusTime(System.currentTimeMillis() + 100);
		case INPROGRESS:
			ordStatus.setStatus("Water delivery to farm "+ order.getFarmId() + " started");
			ordStatus.setOrderStatusTime(order.getStartDateTime().getTime());
		case DELIVERED:
			ordStatus.setStatus("Water delivery to farm "+ order.getFarmId() + " stopped");
			ordStatus.setOrderStatusTime(order.getStartDateTime().getTime() + order.getDuration()*60000);		
		}
		statusPublisher.publishOrderStatus(ordStatus);
	}

	/**
	 * Cancel sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	public SalesOrder cancelSalesOrder(String salesOrderId) throws PersistenceException {
		SalesOrder ord = salesOrderRepository.cancelSalesOrder(salesOrderId);
		createAlert(ord, Status.CANCELLED);
		return ord;
	}

	/**
	 * Find orders by start date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	public List<SalesOrder> findOrdersByStartDateTime(Date currentDateTime) throws PersistenceException {
		return salesOrderRepository.findOrdersByStartDateTime(currentDateTime);
	}

	/**
	 * Find orders by end date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	public List<SalesOrder> findOrdersByEndDateTime(Date currentDateTime) throws PersistenceException {
		return salesOrderRepository.findOrdersByEndDateTime(currentDateTime);
	}

	/**
	 * Find sales order by farm id.
	 *
	 * @param farmId the farm id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	public List<SalesOrder> findSalesOrderByFarmId(String farmId) throws PersistenceException {
		return salesOrderRepository.findSalesOrderByFarmId(farmId);
	}

	/**
	 * Find sales order by farm id.
	 *
	 * @param farmId the farm id
	 * @param startDateTime the start date time
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	public List<SalesOrder> findSalesOrderByFarmId(String farmId, Date startDateTime) throws PersistenceException {
		List<SalesOrder> orders = findSalesOrderByFarmId(farmId);
		if(orders != null && orders.size() > 0) {
			List<SalesOrder> orderList = orders.stream().filter(t -> t.getStartDateTime()
					.equals(startDateTime)).collect(Collectors.toList());
			return orderList;
		}
		throw new PersistenceException("findSalesOrderByFarmId: No SalesOrder found", null);
	}

	/**
	 * Gets the status publisher.
	 *
	 * @return the status publisher
	 */
	public OrderStatusPublisher getStatusPublisher() {
		return statusPublisher;
	}
}
