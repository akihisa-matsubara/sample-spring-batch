package jp.co.springbatch.sample.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.springbatch.sample.biz.tasklet.ImportUser;
import jp.co.springbatch.sample.common.listener.JobExecutionLoggingListener;

@Configuration
@EnableBatchProcessing
public class ImportUserTasklet {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ImportUser importUser;

	// tag::jobstep[]
	@Bean
	public Job importUserJobTasklet(JobExecutionLoggingListener listener, Step taskletStep) throws Exception {
		return jobBuilderFactory.get("importUserJobTasklet")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(taskletStep)
				.build();
	}

	@Bean
	public Step taskletStep() {
		return stepBuilderFactory.get("taskletStep")
				.tasklet(importUser)
				.build();
	}
	// end::jobstep[]
}
