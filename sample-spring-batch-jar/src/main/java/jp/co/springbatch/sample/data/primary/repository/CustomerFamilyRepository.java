package jp.co.springbatch.sample.data.primary.repository;

import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 顧客家族リポジトリー.
 */
@Mapper
public interface CustomerFamilyRepository {

  /** sql id: selectAll. */
  public static final String SELECT_ALL = "jp.co.springbatch.sample.data.primary.repository.CustomerFamilyRepository.selectAll";

  /**
   * 全件取得.
   *
   * @return {@code List<CustomerFamilyEntity>} 取得結果
   */
  List<CustomerFamilyEntity> selectAll();

}
