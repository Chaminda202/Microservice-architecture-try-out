package com.currency.exchange.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currency.exchange.common.CommonConstantValue;
import com.currency.exchange.request.ExchangeRequest;
import com.currency.exchange.service.ExchangeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/currencies")
public class CurrencyExchangeController {
	private Logger logger;
	private ExchangeService exchangeService;
	
	public CurrencyExchangeController(ExchangeService exchangeService) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.exchangeService = exchangeService;
	}
	/*
	 * @Autowired private Environment environment;
	 * 
	 * @GetMapping public String hello() { return
	 * "Currency exchange controller...!!!"; }
	 * 
	 * @GetMapping(value = "/from/{from}/to/{to}") public ExchangeResponse
	 * retrieveExchangeValue(@ApiParam(required = true, name = "from", value =
	 * "From cannot be missing or empty") @PathVariable String from,
	 * 
	 * @ApiParam(required = true, name = "to", value =
	 * "To cannot be missing or empty") @PathVariable String to) { return
	 * ExchangeResponse.builder() .from(from) .to(to) .conversionMultiple(new
	 * BigDecimal(173.56))
	 * .port(Integer.parseInt(this.environment.getProperty("local.server.port")))
	 * .build(); }
	 */
	
	@ApiOperation(value = "Retrieve exchange value")
	@GetMapping(value = "/from/{from}/to/{to}")
	public Map<String, Object> retrieveExchangeValue(
			@ApiParam(required = true, name = "from", value = "From cannot be missing or empty") @PathVariable String from,
			@ApiParam(required = true, name = "to", value = "To cannot be missing or empty") @PathVariable String to) {
		logger.info("Start retrieve exchange value controller {} -> {}", from, to);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.exchangeService.findByFromAndTo(from, to));
			logger.info("Retrieve exchange value {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error retrieve echange value");
			logger.error("Retrieve exchange value {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "Save exchange value")
	@PostMapping
	public Map<String, Object> saveExchangeValue(@RequestBody ExchangeRequest request) {
		logger.info("Start retrieve exchange value controller {} -> {}", request.getFrom(), request.getTo());
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.exchangeService.save(request));
			logger.info("Retrieve exchange value {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error save exchange value");
			logger.error("Retrieve exchange value {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "Update exchange value")
	@PutMapping(value = "/{id}")
	public Map<String, Object> updateExchangeValue(@RequestBody ExchangeRequest request,
			@ApiParam(required = true, name = "id", value = "Id cannot be missing or empty") @RequestParam Integer id) {
		logger.info("Start update exchange value controller {}", id);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.exchangeService.update(request, id));
			logger.info("Update exchange value {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error update exchange value");
			logger.error("Update exchange value {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "Retrieve list of exchange values")
	@GetMapping
	public Map<String, Object> retrieveExchangeValues() {
		logger.info("Start retrieve all exchange values controller");
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.exchangeService.findAll());
			logger.info("Retrieve all exchange values {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, "Error retrieve all exchange values");
			logger.error("Retrieve all exchange values {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
}
