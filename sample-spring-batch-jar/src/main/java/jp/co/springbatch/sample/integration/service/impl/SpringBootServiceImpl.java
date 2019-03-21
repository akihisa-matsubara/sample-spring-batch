package jp.co.springbatch.sample.integration.service.impl;

import jp.co.springbatch.framework.constant.ScopeConst;
import jp.co.springbatch.sample.integration.dto.QuoteDto;
import jp.co.springbatch.sample.integration.service.SpringBootService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot Service実装.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SpringBootServiceImpl implements SpringBootService {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootServiceImpl.class);

  /** RestTemplate. */
  // thread safe
  private final RestTemplate restTemplate;

  /** API URL. */
  @Value("${sample.service.spring-boot-service.random-api}")
  private String url;

  /**
   * コンストラクタ.
   *
   * @param restTemplateBuilder RestTemplateBuilder
   */
  public SpringBootServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QuoteDto getRandomQuotation() {
    ResponseEntity<QuoteDto> response = restTemplate.getForEntity(url, QuoteDto.class);
    LOGGER.info("SpringBootService get random response: httpStatus=[{}], quote=[{}]", response.getStatusCode(), response.getBody());
    return response.getBody();
  }

}
