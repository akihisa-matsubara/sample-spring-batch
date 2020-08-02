package dev.sample.springbatch.code;

import dev.sample.common.code.CodeVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
