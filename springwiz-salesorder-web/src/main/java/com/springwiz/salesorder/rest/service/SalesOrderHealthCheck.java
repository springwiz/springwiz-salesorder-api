package com.springwiz.salesorder.rest.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class SalesOrderHealthCheck.
 */
@RestController
public class SalesOrderHealthCheck {
	
	/**
	 * Test.
	 *
	 * @return the string
	 */
	@RequestMapping(path="test", method=RequestMethod.GET)
	public String test(){
		return "Test app";
	}
}