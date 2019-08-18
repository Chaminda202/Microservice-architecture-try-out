package com.currency.exchange.service;

import java.util.List;

import com.currency.exchange.exception.ApplicationException;
import com.currency.exchange.request.ExchangeRequest;
import com.currency.exchange.response.ExchangeResponse;

public interface ExchangeService {
	ExchangeResponse save(ExchangeRequest request);
	ExchangeResponse update(ExchangeRequest request, Integer id) throws ApplicationException;
	ExchangeResponse findByFromAndTo(String from, String to) throws ApplicationException;
	List<ExchangeResponse> findAll();
}
