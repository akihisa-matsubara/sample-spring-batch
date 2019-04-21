package jp.co.sample.springbatch.framework.util;

import jp.co.sample.common.code.DateFormatVo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * 日付ユーティリティー(LocaleDateTIme).
 */
// DateTimeFormatterはスレッドセーフなので定数定義してもよい.ただし、STRICT設定すること.
@UtilityClass
public class LocalDateTimeFormatUtils {

  /**
   * Dateを指定フォーマットにフォーマットします.
   *
   * @param dateTime 日付文字列にフォーマットする時刻値
   * @param format 日付フォーマットVO
   * @return フォーマットされた日時文字列.指定された時刻値がnullの場合はnullを返す.
   */
  public static String format(LocalDateTime dateTime, DateFormatVo format) {
    return dateTime == null ? "" : dateTime.format(DateTimeFormatter.ofPattern(format.getApiCode()));
  }

  /**
   * 指定された文字列を解析して日付を生成します.
   *
   * @param text 日付文字列
   * @param format 日付フォーマットVO
   * @return 文字列から解析されるLocalDateTime
   * @throws DateTimeParseException 指定された文字列が解析できない場合
   */
  public static LocalDateTime parse(String text, DateFormatVo format) {
    // 時刻を持たない場合そのまま変換できないので、一度Dateに変換する
    Date date = null;
    switch (format) {
      case YYYYMMDD:
        date = DateFormatUtilsExt.parse(text, DateFormatVo.YYYYMMDD);
        break;
      case YYYYMMDD_NO_DELIMITER:
        date = DateFormatUtilsExt.parse(text, DateFormatVo.YYYYMMDD_NO_DELIMITER);
        break;
      case HHMMSSSSS_NO_DELIMITER:
        throw new IllegalArgumentException("format '" + DateFormatVo.HHMMSSSSS_NO_DELIMITER.getCode() + "' could not be parsed.");
      default:
        break;
    }

    if (date != null) {
      return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.getApiCode());
    formatter.withResolverStyle(ResolverStyle.STRICT);
    return LocalDateTime.parse(text, formatter);
  }

}
