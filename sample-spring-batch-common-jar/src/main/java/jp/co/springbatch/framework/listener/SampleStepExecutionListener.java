package jp.co.springbatch.framework.listener;

import jp.co.springbatch.framework.constant.ScopeConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ステップ実行リスナー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SampleStepExecutionListener extends StepExecutionListenerSupport {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleStepExecutionListener.class);

  /**
   * ステップ開始時処理.
   */
  @Override
  public void beforeStep(StepExecution stepExecution) {
    // do nothing.
  }

  /**
   * ステップ終了時処理.
   */
  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("detailed results of step execution. readCount=[{}], writeCount=[{}], commitCount=[{}], rollbackCount=[{}], "
            + "skipCount=[read=[{}], process=[{}], write=[{}]], filterCount=[{}]",
        stepExecution.getReadCount(),
        stepExecution.getWriteCount(),
        stepExecution.getCommitCount(),
        stepExecution.getRollbackCount(),
        stepExecution.getReadSkipCount(),
        stepExecution.getProcessSkipCount(),
        stepExecution.getWriteSkipCount(),
        stepExecution.getFilterCount());

    return stepExecution.getExitStatus();
  }

}
