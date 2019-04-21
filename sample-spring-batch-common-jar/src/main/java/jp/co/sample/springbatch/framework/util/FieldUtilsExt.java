package jp.co.sample.springbatch.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * フィールドユーティリティー.
 * ※Apache Commons Lang3とクラス名が重複するため別名
 */
@UtilityClass
public class FieldUtilsExt {

  /**
   * フィールドの配列を取得します.
   * static / final なフィールドを取得から除外します.
   * 要素順は定義順となります.
   *
   * @param clazz クラス
   * @return フィールドの配列
   */
  public static String[] getFields(@NonNull Class<?> clazz) {
    return Arrays.stream(FieldUtils.getAllFields(clazz))
        .filter(field -> !Modifier.isStatic(field.getModifiers()))
        .filter(field -> !Modifier.isFinal(field.getModifiers()))
        .map(Field::getName)
        .toArray(String[]::new);
  }

  /**
   * フィールドの配列を取得します.
   * static / final なフィールド、指定したフィールド名を取得から除外します.
   * 要素順は定義順となります.
   *
   * @param clazz クラス
   * @param excludeFields 除外フィールド群
   * @return フィールドの配列
   */
  public static String[] getFields(@NonNull Class<?> clazz, String... excludeFields) {
    return ArrayUtils.removeElements(getFields(clazz), excludeFields);
  }

}
