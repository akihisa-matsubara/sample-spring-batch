package jp.co.springbatch.sample.integration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.springbatch.sample.integration.dto.QuoteDto;
import jp.co.springbatch.sample.integration.service.SpringBootService;

@Service
public class SpringBootServiceImpl implements SpringBootService {

	private static final Logger log = LoggerFactory.getLogger(SpringBootServiceImpl.class);

	private final RestTemplate restTemplate;

	@Value("${sample.spring-boot-service.random-api}")
	private String uri;

	public SpringBootServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public QuoteDto getRandomQuotation() {
		QuoteDto quote = restTemplate.getForObject(uri, QuoteDto.class);
		log.info("SpringBootService random get result:" + quote);
		return quote;
	}

}
