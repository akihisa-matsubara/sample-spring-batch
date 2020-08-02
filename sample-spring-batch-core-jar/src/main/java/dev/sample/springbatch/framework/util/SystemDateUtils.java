package dev.sample.springbatch.framework.util;

import dev.sample.common.util.DateFormat.DateFormatVo;
import dev.sample.common.util.LocalDateFormatUtils;
import dev.sample.springbatch.framework.data.core.repository.SystemDateRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * <PRE>
 * システム日付ユーティリティー.
 * システム日付（みなし日付）を変更したい場合は、以下の方法で設定が可能です.
 * どちらも設定されていた場合、システム日付プロパティの値が優先されます.
 * </PRE>
 * <ul>
 * <li>アプリケーション設定ファイル（{@code application.yml}）</li>
 * <li>システム日付マスタ（{@code M_SYSTEM_DATE}）</li>
 * </ul>
 */
@UtilityClass
@Slf4j
public class SystemDateUtils {

  /** システム日付（みなし日付）（設定ファイル設定値）. */
  private static Optional<LocalDate> propertyDateOpt = Optional.empty();

  /** 初期化済み. */
  private static boolean initialized;

  /** 利用制限. */
  private static boolean restriction;

  /**
   * 初期化.
   *
   * @param use システム日付（みなし日付）利用
   * @param deemedDate システム日付（みなし日付）
   */
  public static void init(boolean use, String deemedDate) {
    if (!use) {
      restriction = true;

    } else if (StringUtils.isNotEmpty(deemedDate)) {
      propertyDateOpt = Optional.of(LocalDateFormatUtils.parse(deemedDate, DateFormatVo.YYYYMMDD_NO_DELIMITER));
      log.info("initialized. systemDate=[{}]", deemedDate);

    }

    initialized = true;
  }

  /**
   * 現在の日付文字列(yyyyMMdd)を取得します.
   * システム日付（みなし日付）に値が設定されている場合、その値を優先します.
   *
   * @return 現在の日付文字列(yyyyMMdd)
   */
  public static String getNowDateAsString() {
    return LocalDateFormatUtils.format(createDate(), DateFormatVo.YYYYMMDD_NO_DELIMITER);
  }

  /**
   * 現在のLocalDateTimeを取得します.
   * システム日付（みなし日付）に値が設定されている場合、その値を優先します.
   *
   * @return 現在のLocalDateTime
   */
  public static LocalDateTime getNowLocalDateTime() {
    return LocalTime.now().atDate(createDate());
  }

  /**
   * システム日付（みなし日付）を作成します.
   * 利用制限がある場合は実際の現在日付を返します.
   * プロパティ／DBともにみなし日付の設定がない場合は、実際の現在日付を返します.
   *
   * @return システム日付（みなし日付）
   */
  private static LocalDate createDate() {
    initializedCheck();

    // 利用制限がある場合は実際の現在日付を返します
    if (restriction) {
      return LocalDate.now();
    }

    return propertyDateOpt.orElseGet(() -> {
      LocalDate deemedDate = SpringContextHelper.getBean(SystemDateRepository.class).select().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return deemedDate != null ? deemedDate : LocalDate.now();
    });
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
