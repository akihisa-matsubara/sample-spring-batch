package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * エンコーディングVO.
 */
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

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private EncodingVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }
}
