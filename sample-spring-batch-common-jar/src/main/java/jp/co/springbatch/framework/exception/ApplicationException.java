package jp.co.springbatch.framework.exception;

/**
 * アプリケーション例外クラス.
 */
public class ApplicationException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  public ApplicationException(Throwable cause) {
    super(cause);
  }

}
