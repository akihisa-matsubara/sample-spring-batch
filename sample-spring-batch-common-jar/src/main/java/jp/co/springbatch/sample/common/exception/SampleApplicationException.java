package jp.co.springbatch.sample.common.exception;

/**
 * サンプルアプリケーション例外クラス.
 */
public class SampleApplicationException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  public SampleApplicationException(Throwable cause) {
    super(cause);
  }

}
