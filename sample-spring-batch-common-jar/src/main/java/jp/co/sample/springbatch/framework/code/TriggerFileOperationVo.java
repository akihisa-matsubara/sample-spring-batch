package jp.co.sample.springbatch.framework.code;

import jp.co.sample.framework.code.CodeVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * トリガーファイル操作VO.
 */
@AllArgsConstructor
@Getter
public enum TriggerFileOperationVo implements CodeVo {

  /** 作成チェック. */
  CREATE_CHECK("CREATE_CHECK", "作成チェック"),
  /** 削除チェック. */
  DELETE_CHECK("DELETE_CHECK", "削除チェック"),
  /** 作成処理. */
  CREATE_PROCCESS("CREATE_PROCCESS", "作成処理"),
  /** 削除処理. */
  DELETE_PROCCESS("DELETE_PROCCESS", "削除処理"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
