package jp.co.springbatch.sample.common.util;

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
import jp.co.springbatch.sample.common.config.properties.SystemDateProperties;
import jp.co.springbatch.sample.common.constant.ScopeCode;

/**
 * 日付ユーティリティー.
 */
@Scope(ScopeCode.SINGLETON)
@Component
public class SampleDateUtils {

  /** yyyyMMdd形式のフォーマッター. */
  private static final SimpleDateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

  /** HHmmssSSS形式のフォーマッター. */
  private static final SimpleDateFormat DATE_FORMAT_HHMMSS = new SimpleDateFormat("HHmmssSSS");

  /** yyyy-MM-dd HH:mm:ss.SSS形式のフォーマッター. */
  private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  /** yyyyMMddHHmmssSSS形式のフォーマッター. */
  private static final DateTimeFormatter DATE_TIME_FOMAT_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleDateUtils.class);

  /** システム日付プロパティ. */
  @Autowired
  private SystemDateProperties systemDateProperties;

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public String getNowDateString() {
    String systemDate = systemDateProperties.getSystemDate();
    return StringUtils.isEmpty(systemDate) ? DATE_FORMAT_YYYYMMDD.format(new Date()) : systemDate;
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public LocalDateTime getNowLocalDateTime() {
    String systemDate = getNowDateString() + DATE_FORMAT_HHMMSS.format(new Date());
    return LocalDateTime.parse(systemDate, DATE_TIME_FOMAT_YYYYMMDDHHMMSS);
  }

  /**
   * Dateを日付文字列(yyyyMMdd)にフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @return フォーマットされた日付文字列(yyyyMMdd).指定された時刻値がnullの場合はnullを返す.
   */
  public static String formatDate(Date date) {
    return date == null ? "" : DATE_FORMAT_YYYYMMDD.format(date);
  }

  /**
   * Dateを日時文字列(yyyy-MM-dd HH:mm:ss.SSS)にフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @return フォーマットされた日時文字列(yyyy-MM-dd HH:mm:ss.SSS).指定された時刻値がnullの場合はnullを返す.
   */
  public static String formatDateTime(Date date) {
    return date == null ? "" : DATE_FORMAT_DATETIME.format(date);
  }

  /**
   * 指定された文字列を解析して日付を生成します.
   *
   * @param text 日付文字列(yyyyMMdd)
   * @return Date 文字列から解析されるDate
   * @throws RuntimeException 指定された文字列が解析できない場合
   */
  public static Date parseDate(String text) {
    try {
      return DATE_FORMAT_YYYYMMDD.parse(text);

    } catch (ParseException pe) {
      log.error(ExceptionUtils.getStackTrace(pe));
      throw new RuntimeException(pe);

    }
  }
}
