package dev.sample.springbatch.framework.util;

import static org.assertj.core.api.Assertions.*;
import dev.sample.test.util.ClassTestUtils;
import dev.sample.test.util.StringTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FieldUtilsExtTest {

  @DisplayName("FieldUtilsExt.getFields(Class<?> clazz)のテスト")
  @Nested
  class GetFields {
    @DisplayName("正常系")
    @ParameterizedTest
    @CsvSource({
        "final/staticではないフィールドが取得できること, 'status,filePath', java.io.File",
    })
    void test(String desc, String expectedStr, String className) {
      // --- setup -----
      // --- execute ---
      String[] actual = FieldUtilsExt.getFields(ClassTestUtils.forName(className));

      // --- verify ----
      String[] expected = StringTestUtils.split(expectedStr);
      assertThat(actual).as(desc).hasSize(expected.length).contains(expected);
    }

    @DisplayName("異常系")
    @ParameterizedTest
    @CsvSource({
        "nullの場合はNullPointerExceptionをthrowすること、'but is null'のメッセージを含むこと, java.lang.NullPointerException, but is null",
    })
    void abnormalTest(String desc, String expectedThrownClassName, String expectedMessage) {
      // --- setup -----
      // --- execute ---
      // --- verify ----
      assertThatThrownBy(() -> {
        FieldUtilsExt.getFields(null);
      }).as(desc).isInstanceOf(ClassTestUtils.forName(expectedThrownClassName))
          .hasMessageContaining(expectedMessage);
    }
  }

  @DisplayName("FieldUtilsExt.getFields(Class<?> clazz, String... excludeFields)のテスト")
  @Nested
  class GetFieldsExclude {
    @DisplayName("正常系")
    @ParameterizedTest
    @CsvSource({
        "final/staticではないフィールドが取得できること, 'status,filePath', java.io.File, ",
        "excludeで指定したフィールドが除外されること,    'filePath',        java.io.File, 'status'",
    })
    void test(String desc, String expectedStr, String className, String excludeFields) {
      // --- setup -----
      // --- execute ---
      String[] actual = FieldUtilsExt.getFields(ClassTestUtils.forName(className), StringTestUtils.split(excludeFields));

      // --- verify ----
      String[] expected = StringTestUtils.split(expectedStr);
      assertThat(actual).as(desc).hasSize(expected.length).contains(expected);
    }

    @DisplayName("異常系")
    @ParameterizedTest
    @CsvSource({
        "nullの場合はNullPointerExceptionをthrowすること、'but is null'のメッセージを含むこと, java.lang.NullPointerException, but is null, 'filePath'",
    })
    void abnormalTest(String desc, String expectedThrownClassName, String expectedMessage, String excludeFields) {
      // --- setup -----
      // --- execute ---
      // --- verify ----
      assertThatThrownBy(() -> {
        FieldUtilsExt.getFields(null, StringTestUtils.split(excludeFields));
      }).as(desc).isInstanceOf(ClassTestUtils.forName(expectedThrownClassName))
          .hasMessageContaining(expectedMessage);
    }
  }

}
