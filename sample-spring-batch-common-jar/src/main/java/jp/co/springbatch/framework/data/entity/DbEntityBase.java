package jp.co.springbatch.framework.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * エンティティ基底クラス.
 * 共通カラムを定義
 */
@SuperBuilder
@Data
public abstract class DbEntityBase implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 7578479167785893500L;

  /** バージョン. */
  private int version;

  /** 作成ユーザーID. */
  private String creationUserId;

  /** 作成日時. */
  private LocalDateTime creationDate;

  /** 更新ユーザーID. */
  private String updatedUserId;

  /** 更新日時. */
  private LocalDateTime updatedDate;

}
