package jp.co.sample.springbatch.framework.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日付フォーマットVO.
 */
@AllArgsConstructor
@Getter
public enum DateFormatVo implements CodeVo {

  /** yyyy-MM-dd. */
  YYYYMMDD("yyyy-MM-dd", "uuuu-MM-dd", "YYYYMMDD"),
  /** yyyyMMdd. */
  YYYYMMDD_NO_DELIMITER("yyyyMMdd", "uuuuMMdd", "YYYYMMDD_NO_DELIMITER"),
  /** yyyy-MM-dd'T'HH:mm:ss.SSS. */
  YYYYMMDDTHHMMSSSSS("yyyy-MM-dd'T'HH:mm:ss.SSS", "uuuu-MM-dd'T'HH:mm:ss.SSS", "YYYYMMDDTHHMMSSSSS"),
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

}
