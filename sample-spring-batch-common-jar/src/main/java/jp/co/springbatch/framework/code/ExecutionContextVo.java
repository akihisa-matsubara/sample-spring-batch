package jp.co.springbatch.framework.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Execution Context 内で扱うVO.
 */
@AllArgsConstructor
@Getter
public enum ExecutionContextVo implements CodeVo {

  /** エラーレコード. */
  ERROR_RECORDS("error-records", "エラーレコード"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
