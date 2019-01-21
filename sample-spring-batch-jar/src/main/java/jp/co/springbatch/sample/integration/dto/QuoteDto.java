package jp.co.springbatch.sample.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 引用文Dto.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

  /** type. */
  private String type;

  /** Value Dto. */
  private ValueDto value;

}
