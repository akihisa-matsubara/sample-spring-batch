package dev.sample.springbatch.framework.item.mapper;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;

/**
 * Field set mapper.
 */
public class FieldSetMapper<T> extends BeanWrapperFieldSetMapper<T> {

  /**
   * デフォルトコンストラクタ.
   *
   * @param targetType Beanクラス
   */
  public FieldSetMapper(Class<T> targetType) {
    setTargetType(targetType);
  }
}
