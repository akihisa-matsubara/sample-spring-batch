package jp.co.sample.springbatch.integration.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客Dto.
 */
@AllArgsConstructor
@NoArgsConstructor
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
  private LocalDate birthday;

  /** 郵便番号. */
  private String addressZip;

  /** 住所. */
  private String address;

}
