package jp.co.sample.springbatch.framework.util;

import jp.co.sample.springbatch.framework.code.DateFormatVo;
import java.text.ParseException;
import java.util.Date;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 日付フォーマットユーティリティー(Date).
 * ※Apache Commons Lang3とクラス名が重複するため別名
 */
@UtilityClass
public class DateFormatUtilsExt {

  /**
   * Dateを指定フォーマットにフォーマットします.
   *
   * @param date 日付文字列にフォーマットする時刻値
   * @param format 日付フォーマットVO
   * @return フォーマットされた日時文字列.指定された時刻値がnullの場合はnullを返す.
   */
  public static String format(Date date, DateFormatVo format) {
    return date == null ? "" : DateFormatUtils.format(date, format.getCode());
  }

  /**
   * 指定された文字列を解析して日付を生成します.
   *
   * @param text 日付文字列
   * @param format 日付フォーマットVO
   * @return Date 文字列から解析されるDate
   * @throws IllegalArgumentException 指定された文字列が解析できない場合
   */
  public static Date parse(String text, DateFormatVo format) {
    try {
      return DateUtils.parseDateStrictly(text, format.getCode());

    } catch (ParseException pe) {
      throw new IllegalArgumentException(pe);

    }
  }

}
