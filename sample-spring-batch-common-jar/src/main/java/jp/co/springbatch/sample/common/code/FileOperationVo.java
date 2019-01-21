package jp.co.springbatch.sample.common.code;

import lombok.Getter;

/**
 * ファイル操作VO.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Getter
public enum FileOperationVo implements CodeVo {

  /** 保存チェック処理. */
  CHECK_SAVE("CHECK_SAVE", "保存チェック処理"),
  /** 削除チェック処理. */
  CHECK_DELETE("CHECK_DELETE", "削除チェック処理"),
  /** 保存処理. */
  SAVE("SAVE", "保存処理"),
  /** 削除処理. */
  DELETE("DELETE", "削除処理");

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

  /**
   * デフォルトコンストラクタ.
   */
  private FileOperationVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
