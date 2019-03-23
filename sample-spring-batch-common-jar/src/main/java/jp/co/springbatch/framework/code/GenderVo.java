package jp.co.springbatch.framework.code;

import lombok.Getter;

/**
 * 性別VO.
 */
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

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private GenderVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
