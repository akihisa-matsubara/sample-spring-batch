package jp.co.springbatch.sample.data.primary.repository;

import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 郵便番号マスタリポジトリー.
 */
@Mapper
public interface PostCodeRepository {

  /** sql id: insert. */
  public static final String INSERT = "jp.co.springbatch.sample.data.primary.repository.PostCodeRepository.insert";

  /**
   * 1件挿入.
   *
   * @param entity 郵便番号マスタEntity
   * @return int 挿入件数
   */
  int insert(PostCodeEntity entity);

}
