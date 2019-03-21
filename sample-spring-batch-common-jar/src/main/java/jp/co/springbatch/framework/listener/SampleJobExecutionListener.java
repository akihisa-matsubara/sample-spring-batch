package jp.co.springbatch.framework.listener;

import jp.co.springbatch.framework.constant.ScopeConst;
import jp.co.springbatch.framework.util.SampleDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ジョブ実行リスナー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SampleJobExecutionListener extends JobExecutionListenerSupport {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleJobExecutionListener.class);

  /**
   * ジョブ開始時処理.
   */
  @Override
  public void beforeJob(JobExecution jobExecution) {
    // do nothing.
  }

  /**
   * ジョブ終了時処理.
   */
  @Override
  public void afterJob(JobExecution jobExecution) {
    LOGGER.info(
        "detailed results of job execution. jobName=[{}], jobParameter=[{}], "
            + "exitCode=[{}], exitDesctioption=[{}], time=[{}-{}], context=[{}], exceptions=[{}]",
        jobExecution.getJobInstance().getJobName(),
        jobExecution.getJobParameters(),
        jobExecution.getExitStatus().getExitCode(),
        jobExecution.getExitStatus().getExitDescription(),
        SampleDateUtils.formatDateTime(jobExecution.getStartTime()),
        SampleDateUtils.formatDateTime(jobExecution.getEndTime()),
        jobExecution.getExecutionContext(),
        jobExecution.getFailureExceptions());

    jobExecution.getStepExecutions().forEach(stepExecution -> {
      Object errorItem = stepExecution.getExecutionContext().get("ERROR_ITEM");
      if (errorItem != null) {
        LOGGER.error("detected error on this item processing. [step:{}] [item:{}]", stepExecution.getStepName(), errorItem);
      }
    });
  }

}
