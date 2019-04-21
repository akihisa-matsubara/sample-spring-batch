package jp.co.sample.springbatch.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 引用文Dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

  /** type. */
  private String type;

  /** Value Dto. */
  private ValueDto value;

}
