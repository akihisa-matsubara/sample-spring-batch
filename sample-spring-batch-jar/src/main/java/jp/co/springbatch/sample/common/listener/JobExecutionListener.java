package jp.co.springbatch.sample.common.listener;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.data.dto.CustomerFileDto;

@Component
public class JobExecutionListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobExecutionListener.class);
	private static final SimpleDateFormat DATE_TIMESTAMP_MILLISEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobExecutionListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("!!! JOB STARTED!");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("!!! JOB FINISHED! Time to verify the results");
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			jdbcTemplate.query("SELECT name, address, tel FROM TBS_CUSTOMER",
					(rs, row) -> new CustomerFileDto(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3)))
					.forEach(customer -> log.info("Found <" + customer + "> in the database."));
		}

		log.info(
				"jobName=[{}], jobParameter=[{}], exitCode=[{}], exitDesctioption=[{}], time=[{}-{}], context=[{}], exceptions=[{}]",
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
