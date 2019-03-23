package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * 日付フォーマットVO.
 */
@Getter
public enum DateFormatVo implements CodeVo {

  /** yyyy-MM-dd. */
  YYYYMMDD("yyyy-MM-dd", "uuuu-MM-dd", "YYYYMMDD"),
  /** yyyyMMdd. */
  YYYYMMDD_NO_DELIMITER("yyyyMMdd", "uuuuMMdd", "YYYYMMDD_NO_DELIMITER"),
  /** yyyy-MM-dd HH:mm:ss.SSS. */
  YYYYMMDDHHMMSSSSS("yyyy-MM-dd HH:mm:ss.SSS", "uuuu-MM-dd HH:mm:ss.SSS", "YYYYMMDDHHMMSSSSS"),
  /** yyyyMMddHHmmssSSS. */
  YYYYMMDDHHMMSSSSS_NO_DELIMITER("yyyyMMddHHmmssSSS", "uuuuMMddHHmmssSSS", "YYYYMMDDHHMMSSSSS_NO_DELIMITER"),
  /** HHmmssSSS. */
  HHMMSSSSS_NO_DELIMITER("HHmmssSSS", "HHmmssSSS", "HHMMSSSSS_NO_DELIMITER"),
  ;

  /** コード. */
  private String code;

  /** Date and Time APIコード. */
  private String apiCode;

  /** デコード. */
  private String decode;

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param apiCode Date and Time API code
   * @param decode デコード
   */
  private DateFormatVo(String code, String apiCode, String decode) {
    this.code = code;
    this.apiCode = apiCode;
    this.decode = decode;
  }

}
