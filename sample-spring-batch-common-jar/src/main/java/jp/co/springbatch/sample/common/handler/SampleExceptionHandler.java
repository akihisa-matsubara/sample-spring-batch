package jp.co.springbatch.sample.common.handler;

import jp.co.springbatch.sample.common.constant.ScopeConst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 例外ハンドラー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SampleExceptionHandler implements ExceptionHandler {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleExceptionHandler.class);

  /**
   * 例外ハンドラー処理.
   *
   * @param context RepeatContext
   * @param throwable 例外
   */
  @Override
  public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
    log.error("exception handler.");
  }

}
