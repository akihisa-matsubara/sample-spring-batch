package jp.co.sample.springbatch.framework.code;

import jp.co.sample.common.code.CodeVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性別VO.
 */
@AllArgsConstructor
@Getter
public enum GenderVo implements CodeVo {

  /** 男性. */
  MALE("0", "男性"),
  /** 女性. */
  FEMALE("1", "女性"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
