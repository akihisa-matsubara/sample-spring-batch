package jp.co.springbatch.sample.biz.processor;

import org.springframework.batch.item.ItemProcessor;

import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

public class CustomerFamilyItemProcessor implements ItemProcessor<CustomerFamilyEntity, CustomerFamilyFileDto> {

	@Override
	public CustomerFamilyFileDto process(final CustomerFamilyEntity customerFamilyEntity) throws Exception {
		return convert(customerFamilyEntity);
	}

	private CustomerFamilyFileDto convert(final CustomerFamilyEntity customerFamilyEntity) {
		// java.util.Date -> String (yyyyMMdd)
		return new CustomerFamilyFileDto(
				customerFamilyEntity.getCustomerNo(),
				customerFamilyEntity.getCustomerNameKanji(),
				customerFamilyEntity.getCustomerNameKana(),
				customerFamilyEntity.getCustomerGender(),
				SampleDateUtils.getDateString(customerFamilyEntity.getCustomerBirthday()),
				customerFamilyEntity.getFamilyNo(),
				customerFamilyEntity.getFamilyNameKanji(),
				customerFamilyEntity.getFamilyNameKana(),
				customerFamilyEntity.getFamilyGender(),
				SampleDateUtils.getDateString(customerFamilyEntity.getFamilyBirthday()));
	}

}
