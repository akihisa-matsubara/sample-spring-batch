package jp.co.springbatch.sample.data.dto;

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

  /** フィールド定義. */
  public static final String[] FIELD = new String[] {
      "customerNo",
      "customerNameKanji",
      "customerNameKana",
      "customerGender",
      "customerBirthday",
      "familyNo",
      "familyNameKanji",
      "familyNameKana",
      "familyGender",
      "familyBirthday"};

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

}
