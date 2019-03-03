package jp.co.springbatch.sample.data.primary.entity;

import java.util.Date;
import lombok.Data;

/**
 * 顧客家族Entity.
 */
@Data
public class CustomerFamilyEntity {

  /** 顧客番号. */
  private String customerNo;

  /** 顧客氏名漢字. */
  private String customerNameKanji;

  /** 顧客氏名カナ. */
  private String customerNameKana;

  /** 顧客性別. */
  private String customerGender;

  /** 顧客生年月日. */
  private Date customerBirthday;

  /** 家族番号. */
  private String familyNo;

  /** 家族氏名漢字. */
  private String familyNameKanji;

  /** 家族氏名カナ. */
  private String familyNameKana;

  /** 家族性別. */
  private String familyGender;

  /** 家族生年月日. */
  private Date familyBirthday;

}
