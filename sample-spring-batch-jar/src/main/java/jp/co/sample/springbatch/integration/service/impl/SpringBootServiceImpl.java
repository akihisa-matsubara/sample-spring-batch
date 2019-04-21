package jp.co.sample.springbatch.integration.service.impl;

import jp.co.sample.springbatch.framework.constant.ScopeConst;
import jp.co.sample.springbatch.integration.dto.QuoteDto;
import jp.co.sample.springbatch.integration.service.SpringBootService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SpringBootServiceImpl implements SpringBootService {

  /** RestTemplate. */
  // thread safe
  private final RestTemplate restTemplate;

  /** API URL. */
  @Value("${sample.service.spring-boot-service.random-api}")
  private String url;

  /**
   * コンストラクタ.
   *
   * @param restTemplateBuilder {@link RestTemplateBuilder}
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
    log.info("SpringBootService get random response: httpStatus=[{}], quote=[{}]", response.getStatusCode(), response.getBody());
    return response.getBody();
  }

}
