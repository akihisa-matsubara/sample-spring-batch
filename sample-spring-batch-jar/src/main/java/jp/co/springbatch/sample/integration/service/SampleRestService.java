package jp.co.springbatch.sample.integration.service;

import java.util.List;

import jp.co.springbatch.sample.integration.dto.CustomerDto;

public interface SampleRestService {
	CustomerDto getCustomer(String id);

	List<CustomerDto> getCustomers();

	void createCustomers(List<CustomerDto> customers);

	int updateCustomers(List<CustomerDto> customers);

	void deleteCustomer(String id);
}
