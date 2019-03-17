package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * トリガーファイル操作VO.
 */
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

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private TriggerFileOperationVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
