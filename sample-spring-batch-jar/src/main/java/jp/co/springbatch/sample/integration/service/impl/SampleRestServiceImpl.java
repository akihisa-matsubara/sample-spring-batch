package jp.co.springbatch.sample.integration.service.impl;

import jp.co.springbatch.framework.constant.ScopeConst;
import jp.co.springbatch.framework.exception.ApplicationException;
import jp.co.springbatch.framework.integration.dto.IntegerResponseDto;
import jp.co.springbatch.framework.integration.dto.ObjectResponseDto;
import jp.co.springbatch.sample.integration.dto.CustomerDto;
import jp.co.springbatch.sample.integration.dto.CustomerResponseDto;
import jp.co.springbatch.sample.integration.dto.CustomersResponseDto;
import jp.co.springbatch.sample.integration.service.SampleRestService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Sample Rest Service実装.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SampleRestServiceImpl implements SampleRestService {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleRestServiceImpl.class);

  /** RestTemplate. */
  // thread safe
  private final RestTemplate restTemplate;

  /** API URL. */
  @Value("${sample.service.sample-rest-service.customers-api}")
  private String url;

  /**
   * コンストラクタ.
   *
   * @param restTemplateBuilder RestTemplateBuilder
   */
  public SampleRestServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  /**
   *  説明：
   *  サンプルではサービス呼び出し時にエラーハンドリングしない
   *  アプリ要件により再送などエラーハンドリングする場合はResponseErrorHandlerを実装すること
   *  RestClientException
   *    ・HttpClientErrorException       - HTTPステータス4xxの場合
   *    ・HttpServerErrorException       - HTTPステータス5xxの場合
   *    ・UnknownHttpStatusCodeException - 不明なHTTPステータス(ユーザ定義のカスタムコードなど)の場合
   *    ・ResourceAccessException        - IO系のエラーの場合
   *
   *  Get
   *    ・getForObject  - レスポンスボディのみ取得する場合
   *    ・getForEntity  - HTTPステータスコード、レスポンスヘッダ、レスポンスボディを取得する場合
   *    ・exchange      - リクエストヘッダを指定する必要がある場合、コレクションで取得する場合
   *  Post
   *    ・postForEntity - 省略
   *    ・exchange      - 省略
   *  Put
   *    ・put           - 省略
   *    ・exchange      - 省略
   *  Delete
   *    ・delete        - 省略
   *    ・exchange      - 省略
   */

  /**
   * {@inheritDoc}
   */
  @Override
  public CustomerDto getCustomer(String customerNo) {
    ResponseEntity<CustomerResponseDto> response = restTemplate.getForEntity(url + "/{customerNo}", CustomerResponseDto.class, customerNo);
    LOGGER.info("SampleRestService get customer response: httpStatus=[{}], customer=[{}]", response.getStatusCode(), response.getBody());
    return response.getBody().getResponse();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CustomerDto> getCustomers() {
    ResponseEntity<CustomersResponseDto> response =
        restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<CustomersResponseDto>() {});
    LOGGER.info("SampleRestService get customers response: httpStatus=[{}], customers=[{}]", response.getStatusCode(), response.getBody());
    return response.getBody().getResponse();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createCustomers(List<CustomerDto> customers) {
    RequestEntity<List<CustomerDto>> requestEntity = null;
    try {
      requestEntity = RequestEntity.post(new URI(url)).contentType(MediaType.APPLICATION_JSON).body(customers);

    } catch (URISyntaxException use) {
      LOGGER.error(ExceptionUtils.getStackTrace(use));
      throw new ApplicationException(use);

    }

    ResponseEntity<ObjectResponseDto> response = restTemplate.exchange(requestEntity, ObjectResponseDto.class);
    LOGGER.info("SampleRestService create customers: httpStatus=[{}], customers=[{}]", response.getStatusCode(), customers);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int updateCustomers(List<CustomerDto> customers) {
    RequestEntity<List<CustomerDto>> requestEntity = null;
    try {
      requestEntity = RequestEntity.put(new URI(url)).contentType(MediaType.APPLICATION_JSON).body(customers);

    } catch (URISyntaxException use) {
      LOGGER.error(ExceptionUtils.getStackTrace(use));
      throw new ApplicationException(use);

    }

    ResponseEntity<IntegerResponseDto> response = restTemplate.exchange(requestEntity, IntegerResponseDto.class);

    if (response == null || response.getBody() == null) {
      LOGGER.info("SampleRestService update customers: no update.");
      return 0;
    }

    LOGGER.info("SampleRestService update customers: httpStatus=[{}], updateCount=[{}], customers=[{}]", response.getStatusCode(),
        response.getBody(), customers);

    return response.getBody().getResponse() == null ? 0 : response.getBody().getResponse();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteCustomer(String customerNo) {
    restTemplate.delete(url + "/{customerNo}", customerNo);
    LOGGER.info("SampleRestService delete customer response: customerNo=[{}]", customerNo);
  }

}
