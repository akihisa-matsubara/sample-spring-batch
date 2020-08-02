package dev.sample.springbatch.integration.service;

import dev.sample.springbatch.framework.exception.ApplicationException;
import dev.sample.springbatch.integration.dto.CustomerDto;
import java.util.List;

/**
 * Sample Rest Serviceインターフェース.
 */
public interface SampleRestService {

  /**
   * API:/customers/{customerNo}, Get.
   *
   * @param customerNo 顧客番号
   * @return {@link CustomerDto} 取得結果
   */
  CustomerDto getCustomer(String customerNo);

  /**
   * API:/customers, Get.
   *
   * @return {@code List<CustomerDto>} 取得結果
   */
  List<CustomerDto> getCustomers();

  /**
   * API:/customers, Post.
   *
   * @param customers 顧客Dtoリスト
   * @throws ApplicationException URI設定が不正な場合
   */
  void createCustomers(List<CustomerDto> customers);

  /**
   * API:/customers, Put.
   *
   * @param customers 顧客Dtoリスト
   * @return 更新件数
   * @throws ApplicationException URI設定が不正な場合
   */
  int updateCustomers(List<CustomerDto> customers);

  /**
   * API:/customers/{customerNo}, Delete.
   *
   * @param customerNo 顧客番号
   */
  void deleteCustomer(String customerNo);

}
