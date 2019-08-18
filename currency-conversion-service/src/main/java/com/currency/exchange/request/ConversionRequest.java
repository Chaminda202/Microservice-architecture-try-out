package com.currency.exchange.request;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionRequest {
	private String from;
	private String to;
	private BigDecimal quantity;
}
