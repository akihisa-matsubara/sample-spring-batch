package jp.co.sample.springbatch.framework.util;

import jp.co.sample.common.code.DateFormat.DateFormatVo;
import jp.co.sample.common.util.DateFormatUtilsExt;
import jp.co.sample.common.util.LocalDateTimeFormatUtils;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * システム日付ユーティリティー.
 * システム日付を変更したい場合は、application.ymlに
 * framework.system-date: yyyyMMdd形式で設定してください.
 */
@UtilityClass
@Slf4j
public class SystemDateUtils {

  /** システム日付（みなし日付）. */
  private static String systemDate;

  /** 初期化済み. */
  private static boolean initialized;

  /**
   * 初期化.
   *
   * @param systemDate システム日付（みなし日付）
   */
  public static void initailize(String systemDate) {
    SystemDateUtils.systemDate = systemDate;
    SystemDateUtils.initialized = true;
    log.debug("initialized. systemDate=[{}]", systemDate);
  }

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateString() {
    initializedCheck();
    return StringUtils.isEmpty(systemDate) ? DateFormatUtilsExt.format(new Date(), DateFormatVo.YYYYMMDD_NO_DELIMITER) : systemDate;
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowLocalDateTime() {
    initializedCheck();
    return LocalDateTimeFormatUtils.parse(getNowDateString() + DateFormatUtilsExt.format(new Date(), DateFormatVo.HHMMSSSSS_NO_DELIMITER),
        DateFormatVo.YYYYMMDDHHMMSSSSS_NO_DELIMITER);
  }

  /**
   * 初期化できていない場合は不正な状態として例外を投げます.
   */
  private static void initializedCheck() {
    if (!initialized) {
      throw new IllegalStateException("SystemDateUtils is not initailized.");
    }
  }

}
