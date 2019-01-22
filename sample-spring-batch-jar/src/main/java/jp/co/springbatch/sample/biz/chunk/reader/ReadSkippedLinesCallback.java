package jp.co.springbatch.sample.biz.chunk.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jp.co.springbatch.sample.common.constant.ScopeConst;

/**
 * Read Skipped Line Callback.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class ReadSkippedLinesCallback implements LineCallbackHandler {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(ReadSkippedLinesCallback.class);

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
