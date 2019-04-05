package jp.co.springbatch.framework.util;

import jp.co.springbatch.framework.code.DateFormatVo;
import jp.co.springbatch.framework.config.properties.SystemDateProperties;
import jp.co.springbatch.framework.constant.ScopeConst;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * システム日付ユーティリティー.
 * システム日付を変更したい場合は、application.ymlに
 * framework.system-date: yyyyMMdd形式で設定してください.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SystemDateUtils {

  /** システム日付. */
  private static String systemDate;

  /**
   * デフォルトコンストラクタ.
   *
   * @param systemDateProperties システム日付プロパティ
   */
  @Autowired
  private SystemDateUtils(SystemDateProperties systemDateProperties) {
    systemDate = systemDateProperties.getSystemDate();
  }

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateString() {
    return StringUtils.isEmpty(systemDate) ? DateFormatUtilsExt.format(new Date(), DateFormatVo.YYYYMMDD_NO_DELIMITER) : systemDate;
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付プロパティに値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowLocalDateTime() {
    return LocalDateTimeFormatUtils.parse(getNowDateString() + DateFormatUtilsExt.format(new Date(), DateFormatVo.HHMMSSSSS_NO_DELIMITER),
        DateFormatVo.YYYYMMDDHHMMSSSSS_NO_DELIMITER);
  }

}
