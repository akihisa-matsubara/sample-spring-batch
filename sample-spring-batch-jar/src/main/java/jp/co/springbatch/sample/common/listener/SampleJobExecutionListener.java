package jp.co.springbatch.sample.common.listener;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Component
public class SampleJobExecutionListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(SampleJobExecutionListener.class);
	private static final SimpleDateFormat DATE_TIMESTAMP_MILLISEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("!!! JOB STARTED!");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("!!! JOB FINISHED!");
		log.info(
				"detailed results of job execution. jobName=[{}], jobParameter=[{}], exitCode=[{}], exitDesctioption=[{}], time=[{}-{}], context=[{}], exceptions=[{}]",
				jobExecution.getJobInstance().getJobName(),
				jobExecution.getJobParameters(),
				jobExecution.getExitStatus().getExitCode(),
				jobExecution.getExitStatus().getExitDescription(),
				DATE_TIMESTAMP_MILLISEC.format(jobExecution.getStartTime()),
				DATE_TIMESTAMP_MILLISEC.format(jobExecution.getEndTime()),
				jobExecution.getExecutionContext(),
				jobExecution.getFailureExceptions());

		jobExecution.getStepExecutions().forEach(stepExecution -> {
			Object errorItem = stepExecution.getExecutionContext().get("ERROR_ITEM");
			if (errorItem != null) {
				log.error("detected error on this item processing. [step:{}] [item:{}]",
						stepExecution.getStepName(), errorItem);
			}
		});
	}

}
