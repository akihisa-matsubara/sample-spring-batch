package jp.co.springbatch.sample.code;

import jp.co.springbatch.framework.code.CodeVo;
import lombok.Getter;

/**
 * バッチVO.
 */
@Getter
public enum BatchVo implements CodeVo {

  /** fileToDb. */
  FILE_TO_DB("fileToDb", "fileToDb"),
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
  private BatchVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
