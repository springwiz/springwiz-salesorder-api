package com.springwiz.salesorder.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;
import com.springwiz.salesorder.core.model.SalesOrder.Status;

/**
 * The Class SalesOrderRepositoryImpl.
 */
@Repository
@Qualifier("salesOrderRepositoryImpl")
public class SalesOrderRepositoryImpl implements SalesOrderRepository {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(SalesOrderRepositoryImpl.class);
	
	/** The order map. */
	private ConcurrentHashMap<String, SalesOrder> orderMap = new ConcurrentHashMap<>();
	
	/** The farm id order id map. */
	private HashMap<String, List<String>> farmIdOrderIdMap = new HashMap<>();
	
	/** The time map. */
	private HashMap<Date, List<SalesOrder>> timeMap = new HashMap<>();

	/**
	 * Instantiates a new sales order repository impl.
	 */
	public SalesOrderRepositoryImpl() {
	}

	/**
	 * Find sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public SalesOrder findSalesOrder(String salesOrderId) throws PersistenceException {
		return orderMap.get(salesOrderId);
	}

	/**
	 * Creates the sales order.
	 *
	 * @param order the order
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public SalesOrder createSalesOrder(SalesOrder order) throws PersistenceException {
		order.setId(UUID.randomUUID().toString());
		orderMap.put(order.getId(), order);
		if(farmIdOrderIdMap.get(order.getFarmId()) == null) {
			List<String> orderIdList = new ArrayList<>();
			orderIdList.add(order.getId());
			farmIdOrderIdMap.put(order.getFarmId(), orderIdList);
		} else {
			farmIdOrderIdMap.get(order.getFarmId()).add(order.getId());
		}
		if(timeMap.get(order.getStartDateTime()) == null) {
			List<SalesOrder> orderList = new ArrayList<>();
			orderList.add(order);
			timeMap.put(order.getStartDateTime(), orderList);
		} else {
			timeMap.get(order.getStartDateTime()).add(order);
		}
		return order;
	}

	/**
	 * Cancel sales order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public SalesOrder cancelSalesOrder(String salesOrderId) throws PersistenceException {
		SalesOrder order = orderMap.get(salesOrderId);
		if(order != null) {
			order.setStatus(Status.CANCELLED);
			return order;
		}
		throw new PersistenceException("cancelSalesOrder: SalesOrder not found", null);
	}

	/**
	 * Find orders by start date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public List<SalesOrder> findOrdersByStartDateTime(Date currentDateTime) throws PersistenceException {
		List<SalesOrder> orders = timeMap.get(currentDateTime);
		if(orders != null && orders.size() > 0) {
			return orders;
		}
		throw new PersistenceException("findOrdersByStartDateTime: No SalesOrder found", null);
	}

	/**
	 * Find orders by end date time.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public List<SalesOrder> findOrdersByEndDateTime(Date currentDateTime) throws PersistenceException {
		List<SalesOrder> orders = new ArrayList<>();
		orderMap.values().forEach(t -> {
			if(t.getStartDateTime().after(new Date(
					currentDateTime.getTime() - t.getDuration()*60000))) {
				orders.add(t);
			}
		});
		return orders;
	}

	/**
	 * Find sales order by farm id.
	 *
	 * @param farmId the farm id
	 * @return the sales order
	 * @throws PersistenceException the persistence exception
	 */
	@Override
	public List<SalesOrder> findSalesOrderByFarmId(String farmId) throws PersistenceException {
		if(farmIdOrderIdMap.get(farmId) != null) {
			List<SalesOrder> orders = farmIdOrderIdMap.get(farmId).stream().map(t -> orderMap.get(t))
			.collect(Collectors.toList());
			if(orders != null && orders.size() > 0) {
				return orders;
			}
		}
		throw new PersistenceException("findSalesOrderByFarmId: No SalesOrder found", null);
	}
}
