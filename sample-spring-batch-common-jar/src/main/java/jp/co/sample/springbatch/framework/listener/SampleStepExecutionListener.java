package jp.co.sample.springbatch.framework.listener;

import jp.co.sample.springbatch.framework.constant.ScopeConst;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SampleStepExecutionListener extends StepExecutionListenerSupport {

  /**
   * ステップ開始時処理.
   *
   * @param stepExecution {@link StepExecution}
   */
  @Override
  public void beforeStep(StepExecution stepExecution) {
    // do nothing.
  }

  /**
   * ステップ終了時処理.
   *
   * @param stepExecution {@link StepExecution}
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
