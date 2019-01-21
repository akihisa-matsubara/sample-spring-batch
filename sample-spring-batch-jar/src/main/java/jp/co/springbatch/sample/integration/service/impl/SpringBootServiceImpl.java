package jp.co.springbatch.sample.integration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.integration.dto.QuoteDto;
import jp.co.springbatch.sample.integration.service.SpringBootService;

/**
 * Spring Boot Service実装.
 */
@Scope(ScopeCode.SINGLETON)
@Service
public class SpringBootServiceImpl implements SpringBootService {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SpringBootServiceImpl.class);

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
   * 実行.
   * API:/random, Get
   *
   * @return QuoteDto 取得結果
   */
  @Override
  public QuoteDto getRandomQuotation() {
    ResponseEntity<QuoteDto> response = restTemplate.getForEntity(url, QuoteDto.class);
    log.info("SpringBootService get random response: httpStatus=[{}], quote=[{}]", response.getStatusCode(), response.getBody());
    return response.getBody();
  }

}
