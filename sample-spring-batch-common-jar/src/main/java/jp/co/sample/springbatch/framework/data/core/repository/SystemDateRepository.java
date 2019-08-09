package jp.co.sample.springbatch.framework.data.core.repository;

import java.util.Date;
import org.apache.ibatis.annotations.Mapper;

/**
 * システム日付マスタリポジトリー.
 */
@Mapper
public interface SystemDateRepository {

  /**
   * 1件取得.
   *
   * @return systemDate システム日付
   */
  Date select();

}
