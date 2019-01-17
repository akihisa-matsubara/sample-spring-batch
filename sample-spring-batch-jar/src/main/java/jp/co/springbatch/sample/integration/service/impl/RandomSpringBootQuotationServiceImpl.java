package jp.co.springbatch.sample.integration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.springbatch.sample.integration.dto.Quote;
import jp.co.springbatch.sample.integration.service.RandomSpringBootQuotationService;

@Service
public class RandomSpringBootQuotationServiceImpl implements RandomSpringBootQuotationService {

	private static final Logger log = LoggerFactory.getLogger(RandomSpringBootQuotationServiceImpl.class);

	private final RestTemplate restTemplate;

	@Value("${sample.random-spring-boot-quotation-service.uri}")
	private String uri;

	public RandomSpringBootQuotationServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public Quote getQuotation() {
		Quote quote = restTemplate.getForObject(uri, Quote.class);
		log.info("RandomSpringBootQuotationService result:" + quote);
		return quote;
	}

}
