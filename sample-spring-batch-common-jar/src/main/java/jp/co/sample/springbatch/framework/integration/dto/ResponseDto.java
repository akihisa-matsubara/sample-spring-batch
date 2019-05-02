package jp.co.sample.springbatch.framework.integration.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response 基底Dto.
 *
 * @param <T> Responseの型
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ResponseDto<T> {

  /** 結果. */
  private String result;

  /** 実行結果. */
  private T response;

  /** エラー情報. */
  private List<String> errors;

}
