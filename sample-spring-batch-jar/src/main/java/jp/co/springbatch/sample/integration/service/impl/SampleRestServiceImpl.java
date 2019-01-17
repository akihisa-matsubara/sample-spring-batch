package jp.co.springbatch.sample.integration.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.springbatch.sample.integration.dto.CustomerDto;
import jp.co.springbatch.sample.integration.service.SampleRestService;

@Service
public class SampleRestServiceImpl implements SampleRestService {

	private static final Logger log = LoggerFactory.getLogger(SampleRestServiceImpl.class);

	private final RestTemplate restTemplate;

	@Value("${sample.service.sample-rest-service.customers-api}")
	private String url;

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

	@Override
	public CustomerDto getCustomer(String id) {
		ResponseEntity<CustomerDto> response = restTemplate.getForEntity(url + "/{customerId}", CustomerDto.class, id);
		log.info("SampleRestService get customer response: httpStatus=[{}], customer=[{}]", response.getStatusCode(), response.getBody());
		return response.getBody();
	}

	@Override
	public List<CustomerDto> getCustomers() {
		ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>(){});
		log.info("SampleRestService get customers response: httpStatus=[{}], customers=[{}]", response.getStatusCode(), response.getBody());
		return response.getBody();
	}

	@Override
	public void createCustomers(List<CustomerDto> customers) {
		RequestEntity<List<CustomerDto>> requestEntity = null;
		try {
			requestEntity = RequestEntity.post(new URI(url)).contentType(MediaType.APPLICATION_JSON).body(customers);
		} catch (URISyntaxException use) {
			log.error(ExceptionUtils.getStackTrace(use));
			throw new RuntimeException(use);
		}

		ResponseEntity<Object> response = restTemplate.exchange(requestEntity, Object.class);
		log.info("SampleRestService create customers: httpStatus=[{}], customers=[{}]", response.getStatusCode(), customers);
	}

	@Override
	public int updateCustomers(List<CustomerDto> customers) {
		RequestEntity<List<CustomerDto>> requestEntity = null;
		try {
			requestEntity = RequestEntity.put(new URI(url)).contentType(MediaType.APPLICATION_JSON).body(customers);
		} catch (URISyntaxException use) {
			log.error(ExceptionUtils.getStackTrace(use));
			throw new RuntimeException(use);
		}

		ResponseEntity<Integer> response = restTemplate.exchange(requestEntity, Integer.class);
		log.info("SampleRestService update customers: httpStatus=[{}], updateCount=[{}], customers=[{}]", response.getStatusCode(), response.getBody(), customers);

		return 0;
	}

	@Override
	public void deleteCustomer(String id) {
		restTemplate.delete(url + "/{customerId}", id);
		log.info("SampleRestService delete customer response: customerId=[{}]", id);
	}

}
