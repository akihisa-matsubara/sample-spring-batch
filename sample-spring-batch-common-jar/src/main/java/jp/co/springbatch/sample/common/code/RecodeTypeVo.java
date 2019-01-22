package jp.co.springbatch.sample.common.code;

import lombok.Getter;

/**
 * レコード種別VO.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Getter
public enum RecodeTypeVo implements CodeVo {

  /** ヘッダーレコード. */
  HEADER_RECODE("01", "ヘッダーレコード"),
  /** データレコード. */
  DATA_RECODE("02", "データレコード"),
  /** トレーラレコード. */
  TRAILER_RECORD("03", "トレーラレコード"),
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
  private RecodeTypeVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
