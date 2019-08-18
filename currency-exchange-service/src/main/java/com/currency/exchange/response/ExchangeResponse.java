package com.currency.exchange.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeResponse {
	private Integer id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	private Integer port;
}
