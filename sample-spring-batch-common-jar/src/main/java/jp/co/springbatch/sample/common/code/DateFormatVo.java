package jp.co.springbatch.sample.common.code;

import lombok.Getter;

/**
 * ファイル操作VO.
 */
@Getter
public enum DateFormatVo implements CodeVo {

  /** yyyyMMdd. */
  YYYYMMDD_NO_DELIMITER("YYYYMMDD_NO_DELIMITER", "yyyyMMdd"),
  /** yyyy-MM-dd HH:mm:ss.SSS. */
  YYYYMMDDHHMMSSSSS("YYYYMMDDHHMMSSSSS", "yyyy-MM-dd HH:mm:ss.SSS"),
  /** yyyyMMddHHmmssSSS. */
  YYYYMMDDHHMMSSSSS_NO_DELIMITER("YYYYMMDDHHMMSSSSS_NO_DELIMITER", "yyyyMMddHHmmssSSS"),
  /** HHmmssSSS. */
  HHMMSSSSS_NO_DELIMITER("HHMMSSSSS_NO_DELIMITER", "HHmmssSSS"),
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
