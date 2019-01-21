package jp.co.springbatch.sample.data.primary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

/**
 * 顧客家族リポジトリー.
 */
@Mapper
public interface CustomerFamilyRepository {

  /**
   * 全件取得.
   *
   * @return {@code List<CustomerFamilyEntity>} 取得結果
   */
  List<CustomerFamilyEntity> selectAll();

}
