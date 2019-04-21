package jp.co.sample.springbatch.integration.service;

import jp.co.sample.springbatch.integration.dto.QuoteDto;

/**
 * Spring Boot Serviceインターフェース.
 */
public interface SpringBootService {

  /**
   * 実行.
   * API:/random, Get
   *
   * @return {@link QuoteDto} 取得結果
   */
  QuoteDto getRandomQuotation();

}
