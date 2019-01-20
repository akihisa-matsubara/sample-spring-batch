package jp.co.springbatch.sample.config.job;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

import jp.co.springbatch.sample.biz.callback.WriteFooterFlatFileCallback;
import jp.co.springbatch.sample.biz.callback.WriteHeaderFlatFileCallback;
import jp.co.springbatch.sample.biz.processor.CustomerFamilyItemProcessor;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.common.code.EncodingVo;
import jp.co.springbatch.sample.common.code.FileOperationVo;
import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

@Scope(ScopeVo.SINGLETON)
@Configuration
@EnableBatchProcessing
public class DbToFileJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private WriteHeaderFlatFileCallback writeHeaderFlatFileCallback;

	@Autowired
	private WriteFooterFlatFileCallback writeFooterFlatFileCallback;

	@Value("${sample.file.db-to-file.path}")
	private String dbToFilePath;

	@Value("${sample.file.trigger-file.path}")
	private String triggerFilePath;

	@Value("${sample.file.trigger-file.name}")
	private String triggerFileName;

	/** job configurations */
	@Bean
	public Job dbToFileJob(SampleJobExecutionListener jobExecutionListener,
			Step dbToFileCheckTriggerFileStep,
			Step dbToFileStep,
			Step dbToFileOutputTriggerFileStep) throws Exception {
		return jobs.get("dbToFileJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.start(dbToFileCheckTriggerFileStep)
				.next(dbToFileStep)
				.on(ExitStatus.COMPLETED.getExitCode()).to(dbToFileOutputTriggerFileStep)
				.end()
				.build();
	}

	/** step configurations */
	@Bean
	public Step dbToFileCheckTriggerFileStep() {
		return steps.get("dbToFileCheckTriggerFileStep")
				.tasklet(dbToFileCheckTriggerFileTasklet())
				.build();
	}

	@Bean
	public Step dbToFileStep(FlatFileItemWriter<CustomerFamilyFileDto> dbToFileItemWriter, SqlSessionFactory primarySqlSessionFactory) {
		return steps.get("dbToFileStep")
				.<CustomerFamilyEntity, CustomerFamilyFileDto> chunk(10)
				.reader(dbToFileItemReader(primarySqlSessionFactory))
				.processor(dbToFileItemProcessor())
				.writer(dbToFileItemWriter)
				.faultTolerant()
				.skipLimit(10)
				.skip(FlatFileParseException.class)
				.noSkip(IOException.class)
				.build();
	}

	@Bean
	public Step dbToFileOutputTriggerFileStep() {
		return steps.get("dbToFileOutputTriggerFileStep")
				.tasklet(dbToFileOutputTriggerFileTasklet())
				.build();
	}

	@Bean
	public TriggerFileTasklet dbToFileCheckTriggerFileTasklet() {
		TriggerFileTasklet tasklet = new TriggerFileTasklet();
		tasklet.setOperation(FileOperationVo.CHECK_SAVE);
		tasklet.setFilePath(triggerFilePath);
		tasklet.setFileName(triggerFileName);
		return tasklet;
	}

	@Bean
	public TriggerFileTasklet dbToFileOutputTriggerFileTasklet() {
		TriggerFileTasklet tasklet = new TriggerFileTasklet();
		tasklet.setOperation(FileOperationVo.SAVE);
		tasklet.setFilePath(triggerFilePath);
		tasklet.setFileName(triggerFileName);
		return tasklet;
	}

	/** reader processor writer configurations */
	@Bean
	public MyBatisCursorItemReader<CustomerFamilyEntity> dbToFileItemReader(SqlSessionFactory primarySqlSessionFactory) {
		MyBatisCursorItemReader<CustomerFamilyEntity> reader = new MyBatisCursorItemReader<>();
		reader.setSqlSessionFactory(primarySqlSessionFactory);
		reader.setQueryId("jp.co.springbatch.sample.data.primary.repository.CustomerFamilyRepository.selectAll");
		return reader;
	}

	@Bean
	public CustomerFamilyItemProcessor dbToFileItemProcessor() {
		return new CustomerFamilyItemProcessor();
	}

	@Bean
	public FlatFileItemWriter<CustomerFamilyFileDto> dbToFileItemWriter() {
		return new FlatFileItemWriterBuilder<CustomerFamilyFileDto>()
				.name("dbToFileItemWriter")
				.resource(new FileSystemResource(dbToFilePath))
				.delimited()
				.delimiter(",")
				.names(CustomerFamilyFileDto.FIELD)
				.encoding(EncodingVo.MS932)
				.headerCallback(writeHeaderFlatFileCallback)
				.footerCallback(writeFooterFlatFileCallback)
				.build();
	}
}
