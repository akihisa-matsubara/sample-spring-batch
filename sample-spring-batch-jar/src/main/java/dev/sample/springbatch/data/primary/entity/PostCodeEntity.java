package dev.sample.springbatch.data.primary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import dev.sample.springbatch.framework.data.entity.DbBaseEntity;

/**
 * 郵便番号マスタEntity.
 */
@SuperBuilder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCodeEntity extends DbBaseEntity {

  /** serialVersionUID. */
  private static final long serialVersionUID = -3324390606538995849L;

  /** 郵便番号. */
  private String postCode;

  /** 都道府県名. */
  private String prefectureName;

  /** 市区町村名. */
  private String cityName;

  /** 町域名. */
  private String townName;

}
