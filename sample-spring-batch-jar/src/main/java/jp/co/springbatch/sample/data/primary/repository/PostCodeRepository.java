package jp.co.springbatch.sample.data.primary.repository;

import org.apache.ibatis.annotations.Mapper;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

/**
 * 郵便番号マスタリポジトリー.
 */
@Mapper
public interface PostCodeRepository {

  /**
   * 1件挿入.
   *
   * @param record 郵便番号マスタEntity
   * @return int 挿入件数
   */
  int insert(PostCodeEntity record);

}
