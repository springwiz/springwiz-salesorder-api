package com.springwiz.salesorder.rest.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springwiz.salesorder.core.dao.exception.PersistenceException;
import com.springwiz.salesorder.core.model.SalesOrder;
import com.springwiz.salesorder.core.service.SalesOrderManager;
import com.springwiz.salesorder.rest.model.ApiError;
import com.springwiz.salesorder.rest.model.ApiResponse.ResponseStatus;
import com.springwiz.salesorder.rest.model.ApiSuccess;
import com.springwiz.salesorder.rest.model.SalesOrderDTO;
import com.springwiz.salesorder.rest.model.mapper.ValueObjectMapper;
import com.sun.tools.sjavac.Log;

/**
 * The Class SalesOrderService.
 *
 * @author sumit REST End Point for the Sales Order Entity
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/salesorder")
public class SalesOrderService {

	/** Logger Instance. */
	private static final Logger logger = LoggerFactory.getLogger("SalesOrderService.class");

	/** The value object mapper. */
	@Autowired
	private ValueObjectMapper valueObjectMapper;

	/** The sales order manager. */
	@Autowired
	private SalesOrderManager salesOrderManager;

	/**
	 * Create Order.
	 *
	 * @param salesOrderDTO the sales order DTO
	 * @return the response entity
	 * @api {put} create
	 * @apiName createOrder
	 * @apiGroup SalesOrder
	 * @apiParam (Object) {Object} salesOrder SalesOrderDTO
	 * @apiSuccess {Boolean} success success.
	 * @apiError {json} Error-Response: { "status": "error" "message": "" }
	 */
	@RequestMapping(value = "create", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Object> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
		logger.info("\n**Entry: createSalesOrder**" + " farmId: " + salesOrderDTO.getFarmId());

		try {
			final SalesOrder ord = valueObjectMapper.map(salesOrderDTO, SalesOrder.class);
			List<SalesOrder> ordList = new ArrayList<>(); 
			try {
				ordList = salesOrderManager.findSalesOrderByFarmId(ord.getFarmId());
			}
			catch(PersistenceException e) {
				logger.warn("No previous orders found for Farm: "+ord.getFarmId());
			}
			if(ordList != null && ordList.size() > 0) {
				List<SalesOrder> ordListMatched = ordList.stream().filter(t -> {
					long ordTime = ord.getStartDateTime().getTime();
					long sTime = t.getStartDateTime().getTime();
					long eTime = sTime + t.getDuration()*60000;
					return ordTime > sTime && ordTime < eTime;
				}).collect(Collectors.toList());
				if(ordListMatched.size() > 0) {
					return new ResponseEntity<Object>(new ApiError(new Exception("Overlapping waterorder present")), 
						HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			return new ResponseEntity<Object>(new ApiSuccess(salesOrderManager.createSalesOrder(ord).getId(), 
				ResponseStatus.SUCCESS, "Order Created"), HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Cancel Order.
	 *
	 * @param salesOrderId the sales order id
	 * @return the response entity
	 * @api {delete} cancel/{salesOrderId}
	 * @apiName cancelOrder
	 * @apiGroup SalesOrder
	 * @apiSuccess {Boolean} success success.
	 * @apiError {json} Error-Response: { "status": "error" "message": "" }
	 */
	@RequestMapping(value = "delete/{salesOrderId}", method = RequestMethod.DELETE, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Object> cancelSalesOrder(@PathVariable String salesOrderId) {
		logger.info("\n**Entry: cancelSalesOrder**" + " salesOrderId: " + salesOrderId);
		try {
			salesOrderManager.cancelSalesOrder(salesOrderId);
			return new ResponseEntity<Object>(new ApiSuccess(salesOrderId, ResponseStatus.SUCCESS, "Order Cancelled"), HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Find SalesOrder by Farm Id.
	 *
	 * @param farmId
	 *            Farm Id
	 * @return the response
	 * 
	 * @api {get} find/farm/{farmId} Find SalesOrder by Farm Id
	 * @apiName findSalesOrderByFarmId
	 * @apiGroup SalesOrder
	 * @apiParam (String) {String} farmId Farm Id
	 * @apiSuccess {Boolean} success success.
	 * @apiError {json} Error-Response: { "status": "error" "message": "" }
	 */
	@RequestMapping(value = "find/farm/{farmId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> findSalesOrderByFarmId(@PathVariable String farmId) {
		logger.info("\n**Entry: findSalesOrderByFarmId**" + " farmId: " + farmId);

		try {
			List<SalesOrder> orderList = salesOrderManager.findSalesOrderByFarmId(farmId);
			List<SalesOrderDTO> orderDTOList = orderList.stream().map(t -> valueObjectMapper.map(t, SalesOrderDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<Object>(orderDTOList, HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Find SalesOrder by SalesOrder Id.
	 *
	 * @param salesOrderId
	 *            SalesOrder Id
	 * @return the response
	 * 
	 * @api {get} find/salesorder/{salesOrderId} Find SalesOrder by SalesOrder Id
	 * @apiName findSalesOrderBySalesOrderId
	 * @apiGroup SalesOrder
	 * @apiParam (String) {String} salesOrderId SalesOrder Id
	 * @apiSuccess {Boolean} success success.
	 * @apiError {json} Error-Response: { "status": "error" "message": "" }
	 */
	@RequestMapping(value = "find/salesorder/{salesOrderId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> findSalesOrderBySalesOrderId(@PathVariable String salesOrderId) {
		logger.info("\n**Entry: findSalesOrderBySalesOrderId**" + " salesOrderId: " + salesOrderId);

		try {
			SalesOrder order = salesOrderManager.findSalesOrder(salesOrderId);
			SalesOrderDTO ord = valueObjectMapper.map(order, SalesOrderDTO.class);
			return new ResponseEntity<Object>(ord, HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Find SalesOrder by Farm Id and startDateTime.
	 *
	 * @param farmId
	 *            Farm Id
	 * @param startDateTime
	 *            startDateTime           
	 * @return the response
	 * 
	 * @api {get} find/farm/{farmId}/date/{startDateTime} 
	 * 	Find SalesOrder by Farm Id And startDateTime
	 * @apiName findSalesOrderByFarmIdAndStartDateTime
	 * @apiGroup SalesOrder
	 * @apiParam (String) {String} farmId Farm Id
	 * @apiParam (String) {String} startDateTime startDateTime
	 * @apiSuccess {Boolean} success success.
	 * @apiError {json} Error-Response: { "status": "error" "message": "" }
	 */
	@RequestMapping(value = "find/farm/{farmId}/date/{startDateTime}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> findSalesOrderByFarmIdAndStartDateTime(
			@PathVariable String farmId, @PathVariable String startDateTime) {
		logger.info("\n**Entry: findSalesOrderByFarmIdAndStartDateTime**" + " farmId: " + farmId);
		logger.info("\n**Entry: findSalesOrderByFarmIdAndStartDateTime**" + " startDateTime: " + startDateTime);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
		
		try {
			;
			List<SalesOrder> orderList = salesOrderManager.findSalesOrderByFarmId(
					farmId, simpleDateFormat.parse(startDateTime));
			List<SalesOrderDTO> orderDTOList = orderList.stream().map(t -> valueObjectMapper.map(t, SalesOrderDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<Object>(orderDTOList, HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}