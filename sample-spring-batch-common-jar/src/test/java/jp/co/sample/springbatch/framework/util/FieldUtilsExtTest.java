package jp.co.sample.springbatch.framework.util;

import static org.assertj.core.api.Assertions.*;
import jp.co.sample.springbatch.framework.util.FieldUtilsExt;
import jp.co.sample.test.util.ClassUtils;
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
        "final/staticではないフィールドが取得できること, java.io.File, 'status,filePath'",
    })
    void test(String desc, String className, String expectedStr) throws ClassNotFoundException {
      // --- setup ---
      // --- execute ---
      String[] actual = FieldUtilsExt.getFields(ClassUtils.forName(className));

      // --- verify ---
      String[] expected = expectedStr.split(",", -1);
      assertThat(actual).hasSize(expected.length).contains(expected);
    }

    @DisplayName("異常系")
    @ParameterizedTest
    @CsvSource({
        "nullの場合はNullPointerExceptionをthrowすること、'but is null'のメッセージを含むこと, java.lang.NullPointerException, but is null",
    })
    void abnormalTest(String desc, String thrownClassName, String message) throws ClassNotFoundException {
      // --- setup ---
      // --- execute ---
      // --- verify ---
      assertThatThrownBy(() -> {
        FieldUtilsExt.getFields(null);
      }).isInstanceOf(ClassUtils.forName(thrownClassName))
          .hasMessageContaining(message);
    }
  }

  @DisplayName("FieldUtilsExt.getFields(Class<?> clazz, String... excludeFields)のテスト")
  @Nested
  class GetFieldsExclude {
    @DisplayName("正常系")
    @ParameterizedTest
    @CsvSource({
        "final/staticではないフィールドが取得できること, java.io.File, , 'status,filePath'",
        "excludeで指定したフィールドが除外されること, java.io.File, 'filePath', 'status'",
    })
    void test(String desc, String className, String excludeFields, String expectedStr) throws ClassNotFoundException {
      // --- setup ---
      // --- execute ---
      String[] actual = FieldUtilsExt.getFields(ClassUtils.forName(className), excludeFields == null ? null : excludeFields.split(",", -1));

      // --- verify ---
      String[] expected = expectedStr.split(",", -1);
      assertThat(actual).hasSize(expected.length).contains(expected);
    }

    @DisplayName("異常系")
    @ParameterizedTest
    @CsvSource({
        "nullの場合はNullPointerExceptionをthrowすること、'but is null'のメッセージを含むこと, 'filePath', java.lang.NullPointerException, but is null",
    })
    void abnormalTest(String desc, String excludeFields, String thrownClassName, String message) throws ClassNotFoundException {
      // --- setup ---
      // --- execute ---
      // --- verify ---
      assertThatThrownBy(() -> {
        FieldUtilsExt.getFields(null, excludeFields.split(",", -1));
      }).isInstanceOf(ClassUtils.forName(thrownClassName))
          .hasMessageContaining(message);
    }
  }

}
