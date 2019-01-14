package jp.co.springbatch.sample.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.springbatch.sample.biz.tasklet.OutputLog;
import jp.co.springbatch.sample.biz.tasklet.OutputTriggerFile;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;

@Configuration
@EnableBatchProcessing
public class OutputLogJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	@Autowired
	private OutputLog outputLog;

	@Autowired
	private OutputTriggerFile outputTriggerFile;

	// tag::jobstep[]
	@Bean
	public Job outputLogJob(JobExecutionListener listener, Step outputLogStep, Step outputTriggerFileStep) throws Exception {
		return jobs.get("outputLogJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(outputLogStep).on(ExitStatus.COMPLETED.getExitCode()).to(outputTriggerFileStep)
				.from(outputLogStep).on(ExitStatus.FAILED.getExitCode()).end()
				.end()
				.build();
	}

	@Bean
	public Step outputLogStep() {
		return steps.get("outputLogStep")
				.tasklet(outputLog)
				.build();
	}

	@Bean
	public Step outputTriggerFileStep() {
		return steps.get("outputTriggerFileStep")
				.tasklet(outputTriggerFile)
				.build();
	}
	// end::jobstep[]
}
