package jp.co.springbatch.sample.data.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客家族FileDto.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerFamilyFileDto {

  static {
    List<String> tmpField = new ArrayList<>();
    tmpField.add("customerNo");
    tmpField.add("customerNameKanji");
    tmpField.add("customerNameKana");
    tmpField.add("customerGender");
    tmpField.add("customerBirthday");
    tmpField.add("familyNo");
    tmpField.add("familyNameKanji");
    tmpField.add("familyNameKana");
    tmpField.add("familyGender");
    tmpField.add("familyBirthday");
    FIELDS = Collections.unmodifiableList(tmpField);
  }

  /** フィールド定義. */
  private static final List<String> FIELDS;

  /** 顧客番号. */
  private String customerNo;

  /** 顧客氏名漢字. */
  private String customerNameKanji;

  /** 顧客氏名カナ. */
  private String customerNameKana;

  /** 顧客性別. */
  private String customerGender;

  /** 顧客生年月日. */
  private String customerBirthday;

  /** 家族番号. */
  private String familyNo;

  /** 家族氏名漢字. */
  private String familyNameKanji;

  /** 家族氏名カナ. */
  private String familyNameKana;

  /** 家族性別. */
  private String familyGender;

  /** 家族生年月日. */
  private String familyBirthday;

  /**
   * フィールド定義を取得します.
   *
   * @return String[] フィールド定義
   */
  public static String[] getFields() {
    return FIELDS.toArray(new String[0]);
  }
}
