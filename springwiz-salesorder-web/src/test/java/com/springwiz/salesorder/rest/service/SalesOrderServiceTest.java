/**
 * 
 */
package com.springwiz.salesorder.rest.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwiz.salesorder.core.model.SalesOrder.Status;
import com.springwiz.salesorder.rest.model.ApiSuccess;
import com.springwiz.salesorder.rest.model.SalesOrderDTO;

/**
 * @author sumitnarayan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class SalesOrderServiceTest {

	@Autowired
	private MockMvc mockMvc;
		
	/** The sales order 1. */
	private SalesOrderDTO salesOrder1;
	
	/** The sales order 1. */
	private SalesOrderDTO salesOrder2;
	
	/** The sales order 1. */
	private SalesOrderDTO salesOrder3;
	
	/** The sales order 1. */
	private SalesOrderDTO salesOrder4;
	
	/** The test date. */
	private Date testDate = new Date();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		salesOrder1 = new SalesOrderDTO();
		salesOrder1.setFarmId("FARM0001");
		salesOrder1.setStatus(Status.REQUESTED);
		salesOrder1.setStartDateTime(testDate);
		salesOrder1.setDuration(3);
		
		salesOrder2 = new SalesOrderDTO();
		salesOrder2.setFarmId("FARM0002");
		salesOrder2.setStatus(Status.REQUESTED);
		salesOrder2.setStartDateTime(testDate);
		salesOrder2.setDuration(3);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
		Date newDate = simpleDateFormat.parse("23022020-180000");
		salesOrder3 = new SalesOrderDTO();
		salesOrder3.setFarmId("FARM0003");
		salesOrder3.setStatus(Status.REQUESTED);
		salesOrder3.setStartDateTime(newDate);
		salesOrder3.setDuration(3);
		
		salesOrder4 = new SalesOrderDTO();
		salesOrder4.setFarmId("FARM0004");
		salesOrder4.setStatus(Status.REQUESTED);
		salesOrder4.setStartDateTime(testDate);
		salesOrder4.setDuration(3);
	}

	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#createSalesOrder(com.springwiz.salesorder.rest.model.SalesOrderDTO)}.
	 */
	@Test
	public void testCreateSalesOrder() {
		try {
			mockMvc.perform(put("/salesorder/create").content(objectMapper.
					writeValueAsString(salesOrder1)).contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("SUCCESS"))
				      .andExpect(jsonPath("$.message").value("Order Created"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#createSalesOrder(com.springwiz.salesorder.rest.model.SalesOrderDTO)}.
	 */
	@Test
	public void testCreateDuplicateSalesOrder() {
		try {
			mockMvc.perform(put("/salesorder/create").content(objectMapper.
					writeValueAsString(salesOrder1)).contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().is5xxServerError())
				      .andExpect(jsonPath("$.message").value("Overlapping waterorder present"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#cancelSalesOrder(java.lang.String)}.
	 */
	@Test
	public void testCancelSalesOrder() {
		try {
			MvcResult res = mockMvc.perform(put("/salesorder/create").content(objectMapper.
					writeValueAsString(salesOrder2)).contentType(MediaType.APPLICATION_JSON)).andReturn();
			ApiSuccess ord = objectMapper.readValue(res.getResponse().getContentAsString(), ApiSuccess.class);
			mockMvc.perform(delete("/salesorder/delete/"+ ord.getCode()).contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("SUCCESS"))
				      .andExpect(jsonPath("$.message").value("Order Cancelled"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#findSalesOrderByFarmId(java.lang.String)}.
	 */
	@Test
	public void testFindSalesOrderByFarmId() {
		try {
			mockMvc.perform(get("/salesorder/find/farm/FARM0001").contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#findSalesOrderBySalesOrderId(java.lang.String)}.
	 */
	@Test
	public void testFindSalesOrderBySalesOrderId() {
		try {
			MvcResult res = mockMvc.perform(put("/salesorder/create").content(objectMapper.
					writeValueAsString(salesOrder4)).contentType(MediaType.APPLICATION_JSON)).andReturn();
			ApiSuccess ord = objectMapper.readValue(res.getResponse().getContentAsString(), ApiSuccess.class);
			mockMvc.perform(get("/salesorder/find/salesorder/"+ord.getCode()).contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.springwiz.salesorder.rest.service.SalesOrderService#findSalesOrderByFarmIdAndStartDateTime(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFindSalesOrderByFarmIdAndStartDateTime() {
		try {
			mockMvc.perform(put("/salesorder/create").content(objectMapper.
					writeValueAsString(salesOrder3)).contentType(MediaType.APPLICATION_JSON)).andReturn();
			mockMvc.perform(get("/salesorder/find/farm/FARM0003/date/23022020-180000").contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
