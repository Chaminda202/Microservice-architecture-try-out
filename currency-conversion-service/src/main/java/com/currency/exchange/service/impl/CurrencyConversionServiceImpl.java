package com.currency.exchange.service.impl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.currency.exchange.config.EndPointsConfig;
import com.currency.exchange.config.GsonUtil;
import com.currency.exchange.exception.ApplicationException;
import com.currency.exchange.feign.client.CurrencyExchangeFeignClient;
import com.currency.exchange.request.ConversionRequest;
import com.currency.exchange.response.ConversionResponse;
import com.currency.exchange.rest.response.ExchangeApiResponse;
import com.currency.exchange.service.CurrencyConversionService;
import com.currency.exchange.util.Util;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
	private Logger logger;
	private EndPointsConfig endPointsConfig;
	private RestTemplate restTemplate;
	private CurrencyExchangeFeignClient currencyExchangeFeignClient;

	public CurrencyConversionServiceImpl(EndPointsConfig endPointsConfig,
			RestTemplate restTemplate, CurrencyExchangeFeignClient currencyExchangeFeignClient) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.endPointsConfig = endPointsConfig;
		this.restTemplate = restTemplate;
		this.currencyExchangeFeignClient = currencyExchangeFeignClient;
	}

	@Override
	public ConversionResponse currencyConversion(ConversionRequest request) throws ApplicationException {
		logger.info("Start currency conversion service {}", GsonUtil.getToString(request, ConversionRequest.class));
		String url = MessageFormat.format(
				Util.urlBuilder(this.endPointsConfig.getExchangeContext(), this.endPointsConfig.getRetrieveExchange()),
				request.getFrom(), request.getTo());

		HttpHeaders header = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(header);

		/*
		 * ResponseEntity<ExchangeApiResponse> responseEntity =
		 * restTemplate.exchange(url, HttpMethod.GET, entity,
		 * ExchangeApiResponse.class);
		 * 
		 * final ExchangeApiResponse response = responseEntity.getBody();
		 * 
		 * ExchangeApiResponse resp = GsonUtil.getFromObject(response,
		 * ExchangeApiResponse.class);
		 */

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		final ExchangeApiResponse response = GsonUtil.getFromObject(responseEntity.getBody(),
				ExchangeApiResponse.class);

		return ConversionResponse.builder().id(response.getDATA().getId()).from(request.getFrom()).to(request.getTo())
				.quantity(request.getQuantity())
				.port(response.getDATA().getPort())
				.conversionMultiple(response.getDATA().getConversionMultiple())
				.totalcalculatedAmount(request.getQuantity().multiply(response.getDATA().getConversionMultiple()))
				.build();

		/*
		 * return
		 * ConversionResponse.builder().from(request.getFrom()).to(request.getTo()).
		 * quantity(request.getQuantity())
		 * .port(this.environment.getProperty("local.server.port") != null ?
		 * Integer.parseInt(this.environment.getProperty("local.server.port")) : null)
		 * .totalcalculatedAmount(new BigDecimal(123.45)).build();
		 */
	}

	@Override
	public ConversionResponse currencyConversionWithFeign(ConversionRequest request) throws ApplicationException {
		logger.info("Start currency conversion with feign service {}", GsonUtil.getToString(request, ConversionRequest.class));
		String responseEntity = currencyExchangeFeignClient
				.retrieveExchangeValue(request.getFrom(), request.getTo());
		final ExchangeApiResponse response = GsonUtil.getFromObject(responseEntity,
				ExchangeApiResponse.class);
		return ConversionResponse.builder().id(response.getDATA().getId()).from(request.getFrom()).to(request.getTo())
				.quantity(request.getQuantity())
				.port(response.getDATA().getPort())
				.conversionMultiple(response.getDATA().getConversionMultiple())
				.totalcalculatedAmount(request.getQuantity().multiply(response.getDATA().getConversionMultiple()))
				.build();
	}
}
