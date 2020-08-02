package dev.sample.springbatch.framework.handler;

import dev.sample.springbatch.framework.constant.ScopeConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 例外ハンドラー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
@Slf4j
public class SampleExceptionHandler implements ExceptionHandler {

  /**
   * 例外ハンドラー処理.
   *
   * @param context RepeatContext
   * @param throwable 例外
   */
  @Override
  public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
    log.error("exception handler. stack trace:{}", ExceptionUtils.getStackTrace(throwable));
  }

}
