package jp.co.sample.springbatch.framework.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エンコーディングVO.
 */
@AllArgsConstructor
@Getter
public enum EncodingVo implements CodeVo {

  /** UTF-8. */
  UTF8("UTF-8", "UTF-8"),
  /** Windows日本語(MS932/windows-31j). */
  MS932("MS932", "MS932"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
