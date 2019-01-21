package jp.co.springbatch.sample.integration.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客Dto.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

  /** 顧客番号. */
  private String customerNo;

  /** 氏名漢字. */
  private String nameKanji;

  /** 氏名カナ. */
  private String nameKana;

  /** 性別. */
  private String gender;

  /** 生年月日. */
  private Date birthday;

  /** 郵便番号. */
  private String addressZip;

  /** 住所. */
  private String address;

}
