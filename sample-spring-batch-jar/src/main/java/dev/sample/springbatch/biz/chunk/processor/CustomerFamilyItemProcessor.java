package dev.sample.springbatch.biz.chunk.processor;

import dev.sample.common.util.DateFormat.DateFormatVo;
import dev.sample.common.util.DateFormatUtilsExt;
import dev.sample.springbatch.data.dto.CustomerFamilyFileDto;
import dev.sample.springbatch.data.primary.entity.CustomerFamilyEntity;
import org.springframework.batch.item.ItemProcessor;

/**
 * 顧客家族ItemProcessor.
 */
public class CustomerFamilyItemProcessor implements ItemProcessor<CustomerFamilyEntity, CustomerFamilyFileDto> {

  /**
   * 処理.
   *
   * @param customerFamilyEntity 顧客家族Entity
   * @return {@link CustomerFamilyFileDto} 顧客家族FileDto
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
   * @return {@link CustomerFamilyFileDto} 顧客家族FileDto
   */
  private CustomerFamilyFileDto convert(final CustomerFamilyEntity customerFamilyEntity) {
    return CustomerFamilyFileDto.builder()
        .customerNo(customerFamilyEntity.getCustomerNo())
        .customerNameKanji(customerFamilyEntity.getCustomerNameKanji())
        .customerNameKana(customerFamilyEntity.getCustomerNameKana())
        .customerGender(customerFamilyEntity.getCustomerGender())
        .customerBirthday(DateFormatUtilsExt.format(customerFamilyEntity.getCustomerBirthday(), DateFormatVo.YYYYMMDD_NO_DELIMITER))
        .familyNo(customerFamilyEntity.getFamilyNo())
        .familyNameKanji(customerFamilyEntity.getFamilyNameKanji())
        .familyNameKana(customerFamilyEntity.getFamilyNameKana())
        .familyRelationship(customerFamilyEntity.getFamilyRelationship())
        .familyGender(customerFamilyEntity.getFamilyGender())
        .familyBirthday(DateFormatUtilsExt.format(customerFamilyEntity.getFamilyBirthday(), DateFormatVo.YYYYMMDD_NO_DELIMITER))
        .build();
  }

}
