/**
 * 
 */
package com.springwiz.salesorder.core.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springwiz.salesorder.core.status.SalesOrderStatus;

/**
 * The Class ConsolePublisher.
 */
@Service
@Qualifier("consolePublisher")
public class ConsolePublisher implements OrderStatusPublisher {

	/** The status Q. */
	private DelayQueue<SalesOrderStatus> statusQ;
 		
    /** The Constant log. */
    private static final Logger log = Logger.getLogger(ConsolePublisher.class.getName());
    
    /** The pool. */
    private ExecutorService pool = Executors.newCachedThreadPool();
    
    /** The simple date format. */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	/**
	 * Instantiates a new console publisher.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ConsolePublisher() throws IOException {
		statusQ = new DelayQueue<SalesOrderStatus>();
		pool.submit(() -> {
			while(true) {
				SalesOrderStatus status = statusQ.take();
				String strTime = simpleDateFormat.format(new Date(status.getOrderStatusTime()));
				System.out.println(Thread.currentThread().getId() + " " + strTime + " " + status.getId()+ " " + status.getStatus());
			}
		});
	}

	/**
	 * Publish order status.
	 *
	 * @param status the status
	 */
	@Override
	public void publishOrderStatus(SalesOrderStatus status) {
		statusQ.put(status);
	}

	/**
	 * Gets the order status count.
	 *
	 * @return the order status count
	 */
	@Override
	public int getOrderStatusCount() {
		return statusQ.size();
	}
}
