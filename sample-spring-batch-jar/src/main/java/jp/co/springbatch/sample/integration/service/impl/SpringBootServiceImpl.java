package jp.co.springbatch.sample.integration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.integration.dto.QuoteDto;
import jp.co.springbatch.sample.integration.service.SpringBootService;

@Scope(ScopeVo.SINGLETON)
@Service
public class SpringBootServiceImpl implements SpringBootService {

	private static final Logger log = LoggerFactory.getLogger(SpringBootServiceImpl.class);

	private final RestTemplate restTemplate;

	@Value("${sample.service.spring-boot-service.random-api}")
	private String url;

	public SpringBootServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public QuoteDto getRandomQuotation() {
		ResponseEntity<QuoteDto> response = restTemplate.getForEntity(url, QuoteDto.class);
		log.info("SpringBootService get random response: httpStatus=[{}], quote=[{}]", response.getStatusCode(), response.getBody());
		return response.getBody();
	}

}
