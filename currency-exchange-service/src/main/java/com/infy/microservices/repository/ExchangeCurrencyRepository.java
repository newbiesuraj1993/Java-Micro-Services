package com.infy.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.microservices.model.ExchangeValue;

public interface ExchangeCurrencyRepository extends JpaRepository<ExchangeValue, Long> {
	ExchangeValue findByFromAndTo(String from,String to);
}
