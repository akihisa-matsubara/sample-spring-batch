package jp.co.springbatch.framework.util;

import static org.assertj.core.api.Assertions.*;
import jp.co.springbatch.framework.code.DateFormatVo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalDateTimeFormatUtilsTest {

  @BeforeAll
  void initAll() {
//    CsvParserSettings
  }

  @DisplayName("LocalDateTimeFormatUtils.format(LocalDateTime dateTime, DateFormatVo format)のテスト")
  @Nested
  class Format {
    @DisplayName("正常系")
    @ParameterizedTest
    @CsvSource({
        "文字列変換できていること, 2014-09-30T06:30:15.123, yyyy-MM-dd'T'HH:mm:ss.SSS, 2014-09-30T06:30:15.123",
        "文字列変換できていること, 2014-09-30T06:30:15.123, yyyyMMddHHmmssSSS, 20140930063015123",
        "nullの場合は空文字であること, , yyyyMMddHHmmssSSS, ''",
    })
    void test(String desc, String dateTimeStr, String formatStr, String expected) throws ClassNotFoundException {
      // --- setup ---
      LocalDateTime dateTime = dateTimeStr == null ? null : LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      DateFormatVo format = CodeUtils.decode(formatStr, DateFormatVo.class);

      // --- execute ---
      String actual = LocalDateTimeFormatUtils.format(dateTime, format);

      // --- verify ---
      assertThat(actual).isEqualTo(expected);
    }
  }

}
