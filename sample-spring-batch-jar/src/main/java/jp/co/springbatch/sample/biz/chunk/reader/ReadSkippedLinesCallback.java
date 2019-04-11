package jp.co.springbatch.sample.biz.chunk.reader;

import jp.co.springbatch.framework.constant.ScopeConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Read Skipped Line Callback.
 */
@Scope(ScopeConst.SINGLETON)
@Component
@Slf4j
public class ReadSkippedLinesCallback implements LineCallbackHandler {

  /**
   * Skip時のコールバック.
   * linesToSkipによりSkipした場合も呼ばれます
   *
   * @param line Skipした行
   */
  @Override
  public void handleLine(String line) {
    log.debug("skipped line: {}", line);
  }

}
