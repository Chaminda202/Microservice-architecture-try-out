package com.currency.exchange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.currency.exchange.entity.Exchange;
import com.currency.exchange.exception.ApplicationException;
import com.currency.exchange.repository.ExchangeRepository;
import com.currency.exchange.request.ExchangeRequest;
import com.currency.exchange.response.ExchangeResponse;
import com.currency.exchange.service.ExchangeService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ExchangeServiceImpl implements ExchangeService {
	private Logger logger;
	private ExchangeRepository exchangeRepository;
	private Environment environment;
	 
	public ExchangeServiceImpl(ExchangeRepository exchangeRepository, Environment environment) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.exchangeRepository = exchangeRepository;
		this.environment = environment;
	}

	@Override
	public ExchangeResponse save(ExchangeRequest request) {
		Exchange createExchange = new Exchange();
		return buildResponse(this.exchangeRepository
				.save(buildEntity(createExchange, request, null)));
	}

	@Override
	public ExchangeResponse update(ExchangeRequest request, Integer id) throws ApplicationException{
		Optional<Exchange> optionalExchange = this.exchangeRepository.findById(id);
		if(optionalExchange.isPresent()) {
			return buildResponse(this.exchangeRepository
					.save(buildEntity(optionalExchange.get(), request, id)));
		}
		throw new ApplicationException("Object not found by id " + id);
	}

	@Override
	public ExchangeResponse findByFromAndTo(String from, String to) throws ApplicationException{
		logger.info("Find by from and to service {} -> {}", from, to);
		Optional<Exchange> optionalExchange = this.exchangeRepository.findByFromValAndToVal(from, to);
		if(optionalExchange.isPresent()) {
			return buildResponse(optionalExchange.get());
		}
		throw new ApplicationException("Object not found by from " + from + " and to " + to);
	}
	
	@Override
	public List<ExchangeResponse> findAll() {
		logger.info("Find all service");
		List<ExchangeResponse> responseList = new ArrayList<>();
		this.exchangeRepository.findAll().forEach(data -> {
			responseList.add(buildResponse(data));
		});
		return responseList;
	}

	private Exchange buildEntity(Exchange exchange, ExchangeRequest request, Integer id) {
		exchange.setId(id);
		exchange.setFromVal(request.getFrom());
		exchange.setToVal(request.getTo());
		exchange.setConversionMultiple(request.getConversionMultiple());
		return exchange;
	}
	
	private ExchangeResponse buildResponse(Exchange exchange) {
		return ExchangeResponse.builder()
				.id(exchange.getId())
				.from(exchange.getFromVal())
				.to(exchange.getToVal())
				.conversionMultiple(exchange.getConversionMultiple())
				.port(this.environment.getProperty("local.server.port") != null ? 
						Integer.parseInt(this.environment.getProperty("local.server.port")): null)
				.build();
	}
}
