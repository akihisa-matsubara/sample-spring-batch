package jp.co.springbatch.sample.config.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.springbatch.sample.biz.tasklet.CallSampleRestService;
import jp.co.springbatch.sample.biz.tasklet.CallSpringBootService;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;

@Configuration
@EnableBatchProcessing
public class CallRestServiceJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	@Autowired
	private CallSpringBootService callSpringBootService;

	@Autowired
	private CallSampleRestService callSampleRestService;

	// tag::jobstep[]
	@Bean
	public Job callRestServiceJob(JobExecutionListener listener, Step callSpringBootServiceStep, Step callSampleRestServiceStep) throws Exception {
		return jobs.get("callRestServiceJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(callSpringBootServiceStep)
				.next(callSampleRestServiceStep)
				.build();
	}

	@Bean
	public Step callSpringBootServiceStep() {
		return steps.get("callSpringBootServiceStep")
				.tasklet(callSpringBootService)
				.build();
	}

	@Bean
	public Step callSampleRestServiceStep() {
		return steps.get("callSampleRestServiceStep")
				.tasklet(callSampleRestService)
				.build();
	}
	// end::jobstep[]
}
