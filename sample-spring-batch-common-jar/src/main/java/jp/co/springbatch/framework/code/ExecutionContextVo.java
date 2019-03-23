package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * Execution Context 内で扱うVO.
 */

@Getter
public enum ExecutionContextVo implements CodeVo {

  /** エラーレコード. */
  ERROR_RECORDS("error-records", "エラーレコード"),
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
  private ExecutionContextVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
