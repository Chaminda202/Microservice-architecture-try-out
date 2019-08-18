package com.currency.exchange.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "exchange-service", url = "localhost:8000")
@FeignClient(name = "exchange-service")
@RibbonClient(name = "exchange-service")
public interface CurrencyExchangeFeignClient {

	@GetMapping(value = "/currencies/from/{from}/to/{to}")
	String retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);

	// @GetMapping(value = "/currencies/from/{from}/to/{to}")
	// ExchangeApiResponse retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
