package com.spring.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservice.config.PropertyConfiguration;
import com.spring.microservice.model.LimitConfiguration;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/configs")
public class LimitConfigurationController {
	@Value("${min.value}")
	private int minValue;
	@Value("${max.value}")
	private int maxValue;
	
	private PropertyConfiguration propertyConfiguration;
	
	public LimitConfigurationController(PropertyConfiguration propertyConfiguration) {
		this.propertyConfiguration = propertyConfiguration;
	}
	
	@ApiOperation(value = "Get static configuration")
	@GetMapping(value = "/static/limits")
	public LimitConfiguration staticConfigurations() {
		return LimitConfiguration.builder()
				.minimum(1)
				.maximum(10)
				.build();
	}
	
	@ApiOperation(value = "Get configuration from the file first way")
	@GetMapping(value = "/first/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations1() {
		return LimitConfiguration.builder()
				.minimum(minValue)
				.maximum(maxValue)
				.build();
	}
	
	@ApiOperation(value = "Get configuration from the file second way")
	@GetMapping(value = "/second/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations2() {
		return LimitConfiguration.builder()
				.minimum(this.propertyConfiguration.getMinValue())
				.maximum(this.propertyConfiguration.getMaxValue())
				.build();
	}
	
	@ApiOperation(value = "Get configuration from the file second third way")
	@GetMapping(value = "/messages")
	public String retrieveMsgFromConfig() {
		return this.propertyConfiguration.getTest() + this.propertyConfiguration.getTemp();
	}
}
