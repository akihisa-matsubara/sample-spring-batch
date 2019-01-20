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
import org.springframework.context.annotation.Scope;

import jp.co.springbatch.sample.biz.tasklet.SampleRestServiceClientTasklet;
import jp.co.springbatch.sample.biz.tasklet.SpringBootServiceClientTasklet;
import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;

@Scope(ScopeVo.SINGLETON)
@Configuration
@EnableBatchProcessing
public class CallRestServiceJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private SpringBootServiceClientTasklet callSpringBootServiceTasklet;

	@Autowired
	private SampleRestServiceClientTasklet callSampleRestServiceTasklet;

	/** job configurations */
	@Bean
	public Job callRestServiceJob(SampleJobExecutionListener jobExecutionListener,
			Step callSpringBootServiceStep,
			Step callSampleRestServiceStep) throws Exception {
		return jobs.get("callRestServiceJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.start(callSpringBootServiceStep)
				.next(callSampleRestServiceStep)
				.build();
	}

	/** step configurations */
	@Bean
	public Step callSpringBootServiceStep() {
		return steps.get("callSpringBootServiceStep")
				.tasklet(callSpringBootServiceTasklet)
				.build();
	}

	@Bean
	public Step callSampleRestServiceStep() {
		return steps.get("callSampleRestServiceStep")
				.tasklet(callSampleRestServiceTasklet)
				.build();
	}
}
