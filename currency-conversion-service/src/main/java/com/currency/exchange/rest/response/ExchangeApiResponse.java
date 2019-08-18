package com.currency.exchange.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeApiResponse {
	private boolean STATUS;
	private Exchange DATA;
	private String MESSAGE;	
}
