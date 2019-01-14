package jp.co.springbatch.sample.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionLoggingListener extends StepExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(StepExecutionLoggingListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// do nothing.
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("step result detail. readCount=[{}], writeCount=[{}], commitCount=[{}], rollbackCount=[{}], skipCount=[read=[{}], process=[{}], write=[{}]]",
				stepExecution.getReadCount(),
				stepExecution.getWriteCount(),
				stepExecution.getCommitCount(),
				stepExecution.getRollbackCount(),
				stepExecution.getReadSkipCount(),
				stepExecution.getProcessSkipCount(),
				stepExecution.getWriteSkipCount());
		return stepExecution.getExitStatus();
	}
}
