package com.springwiz.salesorder.core.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springwiz.salesorder.core.model.SalesOrder.*;
import com.springwiz.salesorder.core.status.SalesOrderStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsolePublisherTest.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConsolePublisher.class})
public class ConsolePublisherTest {
	
	/** The publisher. */
	@Autowired
	@Qualifier("consolePublisher")
	private ConsolePublisher publisher;
	
	/** The status list. */
	private List<SalesOrderStatus> statusList = new ArrayList<>();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		SalesOrderStatus newStatus = new SalesOrderStatus();
		newStatus.setId("ORD0001");
		newStatus.setStatus(Status.REQUESTED.toString());
		newStatus.setOrderStatusTime(System.currentTimeMillis());
		statusList.add(newStatus);
		
		SalesOrderStatus newStatus1 = new SalesOrderStatus();
		newStatus1.setId("ORD0001");
		newStatus1.setStatus(Status.INPROGRESS.toString());
		newStatus1.setOrderStatusTime(System.currentTimeMillis() + 3 * 1000);
		statusList.add(newStatus1);
		
		SalesOrderStatus newStatus2 = new SalesOrderStatus();
		newStatus2.setId("ORD0001");
		newStatus2.setStatus(Status.DELIVERED.toString());
		newStatus2.setOrderStatusTime(System.currentTimeMillis() + 6 * 1000);
		statusList.add(newStatus2);
		
		SalesOrderStatus newStatus4 = new SalesOrderStatus();
		newStatus4.setId("ORD0002");
		newStatus4.setStatus(Status.REQUESTED.toString());
		newStatus4.setOrderStatusTime(System.currentTimeMillis());
		statusList.add(newStatus4);
		
		SalesOrderStatus newStatus5 = new SalesOrderStatus();
		newStatus5.setId("ORD0002");
		newStatus5.setStatus(Status.CANCELLED.toString());
		newStatus5.setOrderStatusTime(System.currentTimeMillis() + 8 * 1000);
		statusList.add(newStatus5);
	}

	/**
	 * Test publish order status.
	 */
	@Test
	public void testPublishOrderStatus() {
		statusList.forEach(status -> publisher.publishOrderStatus(status));
		while(publisher.getOrderStatusCount() > 0) {}
	}

	/**
	 * Test get order status count.
	 */
	@Test
	public void testGetOrderStatusCount() {
		statusList.forEach(status -> publisher.publishOrderStatus(status));
		assert(publisher.getOrderStatusCount() > 0);
	}

}
