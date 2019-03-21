package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * 日付フォーマットVO.
 */
@Getter
public enum DateFormatVo implements CodeVo {

  /** yyyyMMdd. */
  YYYYMMDD_NO_DELIMITER("yyyyMMdd", "YYYYMMDD_NO_DELIMITER"),
  /** yyyy-MM-dd HH:mm:ss.SSS. */
  YYYYMMDDHHMMSSSSS("yyyy-MM-dd HH:mm:ss.SSS", "YYYYMMDDHHMMSSSSS"),
  /** yyyyMMddHHmmssSSS. */
  YYYYMMDDHHMMSSSSS_NO_DELIMITER("yyyyMMddHHmmssSSS", "YYYYMMDDHHMMSSSSS_NO_DELIMITER"),
  /** HHmmssSSS. */
  HHMMSSSSS_NO_DELIMITER("HHmmssSSS", "HHMMSSSSS_NO_DELIMITER"),
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
  private DateFormatVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}