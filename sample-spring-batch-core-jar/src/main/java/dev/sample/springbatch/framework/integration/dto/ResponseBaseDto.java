package dev.sample.springbatch.framework.integration.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response 基底Dto.
 *
 * @param <T> Responseの型
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseBaseDto<T> {

  /** 結果. */
  private String result;

  /** 実行結果. */
  private T response;

  /** エラー情報. */
  private List<String> errors;

}
