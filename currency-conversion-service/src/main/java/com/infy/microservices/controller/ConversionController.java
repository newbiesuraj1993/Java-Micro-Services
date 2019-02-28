package com.infy.microservices.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.microservices.model.ConversionCurrency;
import com.infy.microservices.proxy.CurrencyConversionProxy;

@RestController
public class ConversionController {
	/*
	 * @Autowired private Environment environment;
	 * 
	 */
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CurrencyConversionProxy proxy;
		@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
		public ConversionCurrency convertCurrency(@PathVariable String from, 
				@PathVariable String to, @PathVariable BigDecimal quantity) {
			
			Map<String,String> urimapper=new HashMap<>();
			urimapper.put("from", from);
			urimapper.put("to", to);
			ResponseEntity<ConversionCurrency> entity=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
					ConversionCurrency.class,urimapper);
			return new ConversionCurrency(entity.getBody().getId(), from, to, 
					entity.getBody().getConversionMultiple(), 
					quantity, quantity.multiply(entity.getBody().getConversionMultiple()),
					entity.getBody().getPort());
		}
		
		@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
		public ConversionCurrency convertCurrencyByFeignMethod(@PathVariable String from, 
				@PathVariable String to, @PathVariable BigDecimal quantity) {
			
			ConversionCurrency entity = proxy.retrieveExchangeValue(from, to);
			logger.info("{}",entity);
			return new ConversionCurrency(entity.getId(), from, to, 
					entity.getConversionMultiple(), 
					quantity, quantity.multiply(entity.getConversionMultiple()),
					entity.getPort());
		}
}
