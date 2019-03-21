package jp.co.springbatch.sample.biz.chunk.reader;

import jp.co.springbatch.framework.constant.ScopeConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Read Skipped Line Callback.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class ReadSkippedLinesCallback implements LineCallbackHandler {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ReadSkippedLinesCallback.class);

  /**
   * Skip時のコールバック.
   * linesToSkipによりSkipした場合も呼ばれます
   *
   * @param line Skipした行
   */
  @Override
  public void handleLine(String line) {
    LOGGER.debug("skipped line: {}", line);
  }

}
