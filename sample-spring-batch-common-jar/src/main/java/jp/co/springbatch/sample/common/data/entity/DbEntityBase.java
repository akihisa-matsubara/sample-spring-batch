package jp.co.springbatch.sample.common.data.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * エンティティ基底クラス.
 * 共通カラムを定義
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Data
public abstract class DbEntityBase {

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
