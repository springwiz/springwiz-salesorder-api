package com.springwiz.salesorder.core.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springwiz.salesorder.core.dao.SalesOrderRepositoryImpl;
import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;
import com.springwiz.salesorder.core.model.SalesOrder.Status;

/**
 * The Class SalesOrderManagerTest.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SalesOrderManager.class, SalesOrderRepositoryImpl.class, ConsolePublisher.class})
public class SalesOrderManagerTest {
	
	/** The sales order manager. */
	@Autowired
	private SalesOrderManager salesOrderManager;
	
	/** The sales order 1. */
	private SalesOrder salesOrder1;
	
	/** The test date. */
	private Date testDate = new Date();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		salesOrder1 = new SalesOrder();
		salesOrder1.setFarmId("FARM0001");
		salesOrder1.setStatus(Status.REQUESTED);
		salesOrder1.setStartDateTime(testDate);
		salesOrder1.setDuration(3);
		salesOrder1 = salesOrderManager.createSalesOrder(salesOrder1);
	}

	/**
	 * Test find sales order.
	 */
	@Test
	public void testFindSalesOrder() {
		try {
			assert(salesOrderManager.findSalesOrder(salesOrder1.getId()) != null);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test create sales order.
	 */
	@Test
	public void testCreateSalesOrder() {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setFarmId("FARM0002");
		salesOrder.setStatus(Status.REQUESTED);
		salesOrder.setStartDateTime(testDate);
		salesOrder.setDuration(4);
		try {
			SalesOrder ord = salesOrderManager.createSalesOrder(salesOrder);
			assert(salesOrderManager.findSalesOrder(ord.getId()) != null);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test create alert.
	 */
	@Test
	public void testCreateAlert() {
		assert(salesOrderManager.getStatusPublisher().getOrderStatusCount() > 0);
	}

	/**
	 * Test cancel sales order.
	 */
	@Test
	public void testCancelSalesOrder() {
		try {
			SalesOrder ord = salesOrderManager.cancelSalesOrder(salesOrder1.getId());
			assert(salesOrderManager.findSalesOrder(ord.getId()).getStatus() == Status.CANCELLED);
		} catch (PersistenceException e) {
			assertTrue(false);
		};
	}

	/**
	 * Test find orders by start date time.
	 */
	@Test
	public void testFindOrdersByStartDateTime() {
		try {
			List<SalesOrder> ord = salesOrderManager.findOrdersByStartDateTime(testDate);
			assert(ord.size() > 0);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test find orders by end date time.
	 */
	@Test
	public void testFindOrdersByEndDateTime() {
		try {
			List<SalesOrder> ord = salesOrderManager.findOrdersByEndDateTime(
				new Date(testDate.getTime()+salesOrder1.getDuration()*60000));
			assert(ord.size() > 0);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test find sales order by farm id string.
	 */
	@Test
	public void testFindSalesOrderByFarmIdString() {
		try {
			List<SalesOrder> ord = salesOrderManager.findSalesOrderByFarmId("FARM0001");
			assert(ord.size() > 0);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}
}
