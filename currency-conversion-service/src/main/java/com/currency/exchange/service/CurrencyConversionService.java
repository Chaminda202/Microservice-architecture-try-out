package com.currency.exchange.service;

import com.currency.exchange.exception.ApplicationException;
import com.currency.exchange.request.ConversionRequest;
import com.currency.exchange.response.ConversionResponse;

public interface CurrencyConversionService {
	ConversionResponse currencyConversion(ConversionRequest request) throws ApplicationException;
	ConversionResponse currencyConversionWithFeign(ConversionRequest request) throws ApplicationException;
}
