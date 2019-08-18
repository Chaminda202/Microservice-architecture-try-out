package com.currency.exchange.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.exchange.common.CommonConstantValue;
import com.currency.exchange.request.ConversionRequest;
import com.currency.exchange.service.CurrencyConversionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/conversions")
public class CurrencyConversionController {
	private Logger logger;
	private CurrencyConversionService currencyConversionService;
	
	public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.currencyConversionService = currencyConversionService;
	}
	
	@ApiOperation(value = "Get currency conversion")
	@GetMapping(value = "/from/{from}/to/{to}/quantity/{quantity}")
	public Map<String, Object> currencyConversion(
			@ApiParam(required = true, name = "from", value = "From cannot be missing or empty") @PathVariable String from,
			@ApiParam(required = true, name = "to", value = "To cannot be missing or empty") @PathVariable String to,
			@ApiParam(required = true, name = "quantity", value = "Quantity cannot be missing or empty") @PathVariable BigDecimal quantity) {
		logger.info("Start currency conversion controller {} -> {} -> {}", from, to, quantity);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, 
					this.currencyConversionService
					.currencyConversion(ConversionRequest
							.builder()
							.from(from)
							.to(to)
							.quantity(quantity)
							.build()));
			logger.info("Retrieve exchange value {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error currency conversion");
			logger.error("Retrieve exchange value {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "Get currency conversion")
	@GetMapping(value = "/feign/from/{from}/to/{to}/quantity/{quantity}")
	public Map<String, Object> currencyConversionWithFeign(
			@ApiParam(required = true, name = "from", value = "From cannot be missing or empty") @PathVariable String from,
			@ApiParam(required = true, name = "to", value = "To cannot be missing or empty") @PathVariable String to,
			@ApiParam(required = true, name = "quantity", value = "Quantity cannot be missing or empty") @PathVariable BigDecimal quantity) {
		logger.info("Start currency conversion controller {} -> {} -> {}", from, to, quantity);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, 
					this.currencyConversionService
					.currencyConversionWithFeign(ConversionRequest
							.builder()
							.from(from)
							.to(to)
							.quantity(quantity)
							.build()));
			logger.info("Retrieve exchange value {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error currency conversion");
			logger.error("Retrieve exchange value {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
}
