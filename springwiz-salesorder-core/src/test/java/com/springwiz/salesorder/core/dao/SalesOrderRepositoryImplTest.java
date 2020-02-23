package com.springwiz.salesorder.core.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;
import com.springwiz.salesorder.core.model.SalesOrder.Status;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderRepositoryImplTest.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SalesOrderRepositoryImpl.class})
public class SalesOrderRepositoryImplTest {

	/** The sales order repository. */
	@Autowired
	private SalesOrderRepositoryImpl salesOrderRepository;
	
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
		salesOrder1 = salesOrderRepository.createSalesOrder(salesOrder1);
	}

	/**
	 * Test find sales order.
	 */
	@Test
	public void testFindSalesOrder() {
		try {
			assert(salesOrderRepository.findSalesOrder(salesOrder1.getId()) != null);
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
			SalesOrder ord = salesOrderRepository.createSalesOrder(salesOrder);
			assert(salesOrderRepository.findSalesOrder(ord.getId()) != null);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test cancel sales order.
	 */
	@Test
	public void testCancelSalesOrder() {
		try {
			SalesOrder ord = salesOrderRepository.cancelSalesOrder(salesOrder1.getId());
			assert(salesOrderRepository.findSalesOrder(ord.getId()).getStatus() == Status.CANCELLED);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test find orders by start date time.
	 */
	@Test
	public void testFindOrdersByStartDateTime() {
		try {
			List<SalesOrder> ord = salesOrderRepository.findOrdersByStartDateTime(testDate);
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
			List<SalesOrder> ord = salesOrderRepository.findOrdersByEndDateTime(
				new Date(testDate.getTime()+salesOrder1.getDuration()*60000));
			assert(ord.size() > 0);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

	/**
	 * Test find sales order by farm id.
	 */
	@Test
	public void testFindSalesOrderByFarmId() {
		try {
			List<SalesOrder> ord = salesOrderRepository.findSalesOrderByFarmId("FARM0001");
			assert(ord.size() > 0);
		} catch (PersistenceException e) {
			assertTrue(false);
		}
	}

}
