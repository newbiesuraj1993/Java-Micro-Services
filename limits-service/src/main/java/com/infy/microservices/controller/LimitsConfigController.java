package com.infy.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.microservices.configs.Configuration;
import com.infy.microservices.models.LimitConfiguration;

@RestController
public class LimitsConfigController {
	
	@Autowired
	private Configuration configuration;
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
}
