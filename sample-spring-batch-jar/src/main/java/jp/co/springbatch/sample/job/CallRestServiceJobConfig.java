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

import jp.co.springbatch.sample.biz.tasklet.CallRestService;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;

@Configuration
@EnableBatchProcessing
public class CallRestServiceJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	@Autowired
	private CallRestService callRestService;

	// tag::jobstep[]
	@Bean
	public Job callRestServiceJob(JobExecutionListener listener, Step callRestServiceStep) throws Exception {
		return jobs.get("callRestServiceJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(callRestServiceStep)
				.build();
	}

	@Bean
	public Step callRestServiceStep() {
		return steps.get("callRestServiceStep")
				.tasklet(callRestService)
				.build();
	}
	// end::jobstep[]
}
