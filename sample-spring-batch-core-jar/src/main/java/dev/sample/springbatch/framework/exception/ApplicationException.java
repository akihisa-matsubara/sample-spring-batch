package dev.sample.springbatch.framework.exception;

/**
 * アプリケーション例外クラス.
 */
public class ApplicationException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * デフォルトコンストラクタ.
   *
   * @param cause 例外
   */
  public ApplicationException(Throwable cause) {
    super(cause);
  }

}
