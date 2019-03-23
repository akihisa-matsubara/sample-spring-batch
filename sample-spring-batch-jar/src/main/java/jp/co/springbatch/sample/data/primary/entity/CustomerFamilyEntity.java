package jp.co.springbatch.sample.data.primary.entity;

import jp.co.springbatch.framework.data.entity.DbEntityBase;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 顧客家族Entity.
 */
@SuperBuilder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomerFamilyEntity extends DbEntityBase {

  /** serialVersionUID. */
  private static final long serialVersionUID = -1715084226067248273L;

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
