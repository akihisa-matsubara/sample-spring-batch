package jp.co.springbatch.sample.integration.service;

import jp.co.springbatch.sample.integration.dto.QuoteDto;

/**
 * Spring Boot Serviceインターフェース.
 */
public interface SpringBootService {

  /**
   * 実行.
   * API:/random, Get
   *
   * @return QuoteDto 取得結果
   */
  QuoteDto getRandomQuotation();

}
