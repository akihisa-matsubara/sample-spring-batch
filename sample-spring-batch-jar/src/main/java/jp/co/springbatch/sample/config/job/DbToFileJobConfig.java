package jp.co.springbatch.sample.config.job;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.springbatch.sample.biz.tasklet.OutputLogTasklet;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;
import jp.co.springbatch.sample.data.primary.entity.TbsCustomer;

@Configuration
@EnableBatchProcessing
public class DbToFileJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	@Autowired
	private OutputLogTasklet outputLogTasklet;

	@Value("${sample.file.trigger-file.path}")
	private String triggerFilePath;

	@Value("${sample.file.trigger-file.name}")
	private String triggerFileName;

	// tag::jobstep[]
	@Bean
	public Job dbToFileJob(JobExecutionListener listener, Step outputLogStep, Step outputTriggerFileStep) throws Exception {
		return jobs.get("dbToFileJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(outputLogStep).on(ExitStatus.COMPLETED.getExitCode()).to(outputTriggerFileStep)
				.end()
				.build();
	}

	@Bean
	public Step outputLogStep() {
		return steps.get("outputLogStep")
				.tasklet(outputLogTasklet)
				.build();
	}

	@Bean
	public Step outputTriggerFileStep() {
		return steps.get("outputTriggerFileStep")
				.tasklet(outputTriggerFileTasklet())
				.build();
	}

	@Bean
	public TriggerFileTasklet outputTriggerFileTasklet() {
		TriggerFileTasklet tasklet = new TriggerFileTasklet();
		tasklet.setFilePath(triggerFilePath);
		tasklet.setFileName(triggerFileName);
		return tasklet;
	}
	// end::jobstep[]

	// tag::readerwriterprocessor[]
	@Bean
	public ItemReader<TbsCustomer> myBatisCursorItemReader(SqlSessionFactory primarySqlSessionFactory) {
	    MyBatisCursorItemReader<TbsCustomer> reader = new MyBatisCursorItemReader<>();
	    reader.setSqlSessionFactory(primarySqlSessionFactory);
	    reader.setQueryId("dbaccess.mybatis.SampleMapper.selectStatusAbyCursor");
	    return reader;
	}

	// end::readerwriterprocessor[]
}
