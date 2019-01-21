package jp.co.springbatch.sample.biz.chunk.processor;

import org.springframework.batch.item.ItemProcessor;
import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

/**
 * 顧客家族ItemProcessor.
 */
public class CustomerFamilyItemProcessor implements ItemProcessor<CustomerFamilyEntity, CustomerFamilyFileDto> {

  /**
   * 処理.
   *
   * @param customerFamilyEntity 顧客家族Entity
   * @return CustomerFamilyFileDto 顧客家族FileDto
   * @throws Exception 例外
   */
  @Override
  public CustomerFamilyFileDto process(final CustomerFamilyEntity customerFamilyEntity) throws Exception {
    return convert(customerFamilyEntity);
  }

  /**
   * 顧客家族Entity -> 顧客家族FileDto 変換処理.
   * 生年月日の型変換
   * java.util.Date -> String (yyyyMMdd)
   *
   * @param customerFamilyEntity 顧客家族Entity
   * @return CustomerFamilyFileDto 顧客家族FileDto
   */
  private CustomerFamilyFileDto convert(final CustomerFamilyEntity customerFamilyEntity) {
    return new CustomerFamilyFileDto(customerFamilyEntity.getCustomerNo(),
        customerFamilyEntity.getCustomerNameKanji(), customerFamilyEntity.getCustomerNameKana(),
        customerFamilyEntity.getCustomerGender(),
        SampleDateUtils.formatDate(customerFamilyEntity.getCustomerBirthday()),
        customerFamilyEntity.getFamilyNo(), customerFamilyEntity.getFamilyNameKanji(),
        customerFamilyEntity.getFamilyNameKana(), customerFamilyEntity.getFamilyGender(),
        SampleDateUtils.formatDate(customerFamilyEntity.getFamilyBirthday()));
  }

}
