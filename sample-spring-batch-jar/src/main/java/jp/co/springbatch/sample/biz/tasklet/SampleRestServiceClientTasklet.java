package jp.co.springbatch.sample.biz.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.integration.dto.CustomerDto;
import jp.co.springbatch.sample.integration.service.SampleRestService;

@Scope(ScopeCode.SINGLETON)
@Component
public class SampleRestServiceClientTasklet implements Tasklet {

	@Autowired
	private SampleRestService service;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// 都合上、複数の処理を１つにまとめていますが、業務用アプリでは適切にStepを分解すること

		List<CustomerDto> customers = getCustomers();

		if (0 < customers.size()) {
			getCustomer(customers.get(0).getCustomerNo());
		}

		// 2件INSERT→UPDATE→DELETE ※GET時にPKでOrderされていることが前提
		createCustomers();

		updateCustomers();

		deleteCustomer();

		return RepeatStatus.FINISHED;
	}

	private List<CustomerDto> getCustomers() {
		return service.getCustomers();
	}

	private CustomerDto getCustomer(String customerNo) {
		return service.getCustomer(customerNo);
	}

	private void createCustomers() {
		List<CustomerDto> newCustomers = new ArrayList<>();
		CustomerDto newCustomer1 = new CustomerDto();
		newCustomer1.setNameKanji("さんぷるばっち１");
		newCustomer1.setNameKana("サンプルバッチイチ");
		newCustomer1.setGender("1");
		newCustomer1.setBirthday(SampleDateUtils.parseDate("19800505"));
		newCustomer1.setAddressZip("9999999");
		newCustomer1.setAddress("埼玉県さんぷる");
		newCustomers.add(newCustomer1);

		CustomerDto newCustomer2 = new CustomerDto();
		newCustomer2.setNameKanji("さんぷるばっち２");
		newCustomer2.setNameKana("サンプルバッチニ");
		newCustomer2.setGender("2");
		newCustomer2.setBirthday(SampleDateUtils.parseDate("19831111"));
		newCustomer2.setAddressZip("9999999");
		newCustomer2.setAddress("埼玉県さんぷる");
		newCustomers.add(newCustomer2);

		// createCustomers
		service.createCustomers(newCustomers);
	}

	private void updateCustomers() {
		// getCustomers
		List<CustomerDto> customers = service.getCustomers();

		List<CustomerDto> updateCustomers = new ArrayList<>();

		CustomerDto updateCustomer1 = customers.get(customers.size() - 2);
		updateCustomer1.setAddressZip("UPDATE");
		updateCustomer1.setAddress("UPDATE");
		updateCustomers.add(updateCustomer1);

		CustomerDto updateCustomer2 = customers.get(customers.size() - 1);
		updateCustomer2.setAddressZip("UPDATE");
		updateCustomer2.setAddress("UPDATE");
		updateCustomers.add(updateCustomer2);

		// updateCustomers
		service.updateCustomers(updateCustomers);
	}

	private void deleteCustomer() {
		// getCustomers
		List<CustomerDto> customers = service.getCustomers();

		// deleteCustomers
		service.deleteCustomer(customers.get(customers.size() - 2).getCustomerNo());
		service.deleteCustomer(customers.get(customers.size() - 1).getCustomerNo());
	}
}
