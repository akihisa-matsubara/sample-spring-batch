package jp.co.springbatch.framework.util;

import jp.co.springbatch.framework.code.DateFormatVo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * 日付ユーティリティー(LocaleDateTIme).
 */
// DateTimeFormatterはスレッドセーフなので定数定義してもよい.ただし、STRICT設定すること.
public class LocalDateTimeFormatUtils {

  /**
   * デフォルトコンストラクタ.
   */
  private LocalDateTimeFormatUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Dateを指定フォーマットにフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @param format 日付フォーマットVO
   * @return フォーマットされた日時文字列.指定された時刻値がnullの場合はnullを返す.
   */
  public static String format(LocalDateTime date, DateFormatVo format) {
    return date == null ? "" : DateTimeFormatter.ofPattern(format.getApiCode()).format(date);
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.getApiCode());
    formatter.withResolverStyle(ResolverStyle.STRICT);
    return LocalDateTime.parse(text, formatter);
  }

}
