package com.currency.exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class EndPointsConfig {
	@Value("${currency.exchange.context}")
	private String exchangeContext;
	@Value("${retrieve.exchange}")
	private String retrieveExchange;
}
