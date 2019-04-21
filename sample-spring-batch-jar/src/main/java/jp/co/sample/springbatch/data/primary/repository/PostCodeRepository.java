package jp.co.sample.springbatch.data.primary.repository;

import jp.co.sample.springbatch.data.primary.entity.PostCodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 郵便番号マスタリポジトリー.
 */
@Mapper
public interface PostCodeRepository {

  /**
   * 1件挿入.
   *
   * @param entity {@link PostCodeEntity} 郵便番号マスタEntity
   * @return int 挿入件数
   */
  int insert(PostCodeEntity entity);

}
