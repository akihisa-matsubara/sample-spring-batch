package jp.co.sample.springbatch.data.primary.repository;

import jp.co.sample.springbatch.data.primary.entity.CustomerFamilyEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

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
