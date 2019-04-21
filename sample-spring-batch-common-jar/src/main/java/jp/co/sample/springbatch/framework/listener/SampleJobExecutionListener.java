package jp.co.sample.springbatch.framework.listener;

import jp.co.sample.springbatch.framework.code.DateFormatVo;
import jp.co.sample.springbatch.framework.constant.ScopeConst;
import jp.co.sample.springbatch.framework.util.DateFormatUtilsExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ジョブ実行リスナー.
 */
@Scope(ScopeConst.SINGLETON)
@Component
@Slf4j
public class SampleJobExecutionListener extends JobExecutionListenerSupport {

  /**
   * ジョブ開始時処理.
   *
   * @param jobExecution {@link JobExecution}
   */
  @Override
  public void beforeJob(JobExecution jobExecution) {
    // do nothing.
  }

  /**
   * ジョブ終了時処理.
   *
   * @param jobExecution {@link JobExecution}
   */
  @Override
  public void afterJob(JobExecution jobExecution) {
    String startTime = DateFormatUtilsExt.format(jobExecution.getStartTime(), DateFormatVo.YYYYMMDDTHHMMSSSSS);
    String endTime = DateFormatUtilsExt.format(jobExecution.getEndTime(), DateFormatVo.YYYYMMDDTHHMMSSSSS);
    log.info(
        "detailed results of job execution. jobName=[{}], jobParameter=[{}], "
            + "exitCode=[{}], exitDesctioption=[{}], time=[{}-{}], context=[{}], exceptions=[{}]",
        jobExecution.getJobInstance().getJobName(),
        jobExecution.getJobParameters(),
        jobExecution.getExitStatus().getExitCode(),
        jobExecution.getExitStatus().getExitDescription(),
        startTime,
        endTime,
        jobExecution.getExecutionContext(),
        jobExecution.getFailureExceptions());

    jobExecution.getStepExecutions().forEach(stepExecution -> {
      Object errorItem = stepExecution.getExecutionContext().get("ERROR_ITEM");
      if (errorItem != null) {
        log.error("detected error on this item processing. [step:{}] [item:{}]", stepExecution.getStepName(), errorItem);
      }
    });
  }

}
