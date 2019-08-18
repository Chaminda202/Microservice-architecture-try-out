package com.currency.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.exchange.entity.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Integer>{
	Optional<Exchange> findByFromValAndToVal(String fromVal, String toVal);
}
