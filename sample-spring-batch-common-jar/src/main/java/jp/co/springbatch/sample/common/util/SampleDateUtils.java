package jp.co.springbatch.sample.common.util;

import jp.co.springbatch.sample.common.code.DateFormatVo;
import jp.co.springbatch.sample.common.config.properties.SystemDateProperties;
import jp.co.springbatch.sample.common.constant.ScopeConst;
import jp.co.springbatch.sample.common.exception.SampleApplicationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 日付ユーティリティー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SampleDateUtils {

  /** yyyyMMddHHmmssSSS形式のフォーマッター. */
  private static final DateTimeFormatter DATE_TIME_FOMAT_YYYYMMDDHHMMSSSSS =
      DateTimeFormatter.ofPattern(DateFormatVo.YYYYMMDDHHMMSSSSS_NO_DELIMITER.getCode());

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleDateUtils.class);

  /** システム日付. */
  private static String systemDate;

  /**
   * コンストラクタ.
   *
   * @param systemDateProperties システム日付プロパティ
   */
  @Autowired
  private SampleDateUtils(SystemDateProperties systemDateProperties) {
    systemDate = systemDateProperties.getSystemDate();
  }

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateString() {
    return StringUtils.isEmpty(systemDate) ? new SimpleDateFormat(DateFormatVo.YYYYMMDD_NO_DELIMITER.getCode()).format(new Date()) : systemDate;
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowLocalDateTime() {
    return LocalDateTime.parse(getNowDateString() + new SimpleDateFormat(DateFormatVo.HHMMSSSSS_NO_DELIMITER.getCode()).format(new Date()),
        DATE_TIME_FOMAT_YYYYMMDDHHMMSSSSS);
  }

  /**
   * Dateを日付文字列(yyyyMMdd)にフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @return フォーマットされた日付文字列(yyyyMMdd).指定された時刻値がnullの場合はnullを返す.
   */
  public static String formatDate(Date date) {
    return date == null ? "" : new SimpleDateFormat(DateFormatVo.YYYYMMDD_NO_DELIMITER.getCode()).format(date);
  }

  /**
   * Dateを日時文字列(yyyy-MM-dd HH:mm:ss.SSS)にフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @return フォーマットされた日時文字列(yyyy-MM-dd HH:mm:ss.SSS).指定された時刻値がnullの場合はnullを返す.
   */
  public static String formatDateTime(Date date) {
    return date == null ? "" : new SimpleDateFormat(DateFormatVo.YYYYMMDDHHMMSSSSS.getCode()).format(date);
  }

  /**
   * 指定された文字列を解析して日付を生成します.
   *
   * @param text 日付文字列(yyyyMMdd)
   * @return Date 文字列から解析されるDate
   * @throws SampleApplicationException 指定された文字列が解析できない場合
   */
  public static Date parseDate(String text) {
    try {
      return new SimpleDateFormat(DateFormatVo.YYYYMMDD_NO_DELIMITER.getCode()).parse(text);

    } catch (ParseException pe) {
      log.error(ExceptionUtils.getStackTrace(pe));
      throw new SampleApplicationException(pe);

    }
  }
}
