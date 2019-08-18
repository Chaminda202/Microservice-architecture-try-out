package com.spring.microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PropertyConfiguration {
	@Value("${min.value}")
	private int minValue;
	@Value("${max.value}")
	private int maxValue;
	
	@Value("${msg.test}")
	private String test;
	@Value("${msg.temp}")
	private String temp;
}
