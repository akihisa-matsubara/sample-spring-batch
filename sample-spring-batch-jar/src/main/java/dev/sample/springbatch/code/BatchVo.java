package dev.sample.springbatch.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import dev.sample.common.code.CodeVo;

/**
 * バッチVO.
 */
@AllArgsConstructor
@Getter
public enum BatchVo implements CodeVo {

  /** fileToDb. */
  FILE_TO_DB("fileToDb", "fileToDb"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
