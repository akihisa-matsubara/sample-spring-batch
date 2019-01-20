package jp.co.springbatch.sample.config.job;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

import jp.co.springbatch.sample.biz.processor.PostCodeItemProcessor;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.common.code.FileOperationVo;
import jp.co.springbatch.sample.common.constant.EncodingCode;
import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;
import jp.co.springbatch.sample.common.listener.SampleStepExecutionListener;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

@Scope(ScopeCode.SINGLETON)
@Configuration
@EnableBatchProcessing
public class FileToDbJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("${sample.file.file-to-db.data-file.path}")
	private String dataFilePath;

	@Value("${sample.file.file-to-db.data-file.name}")
	private String dataFileName;

	@Value("${sample.file.file-to-db.trigger-file.path}")
	private String triggerFilePath;

	@Value("${sample.file.file-to-db.trigger-file.name}")
	private String triggerFileName;

	/** job configurations */
	@Bean
	public Job fileToDbJob(SampleJobExecutionListener jobExecutionListener,
			Step fileToDbCheckTriggerFileStep,
			Step fileToDbStep,
			Step fileToDbDeleteTriggerFileStep) {
		return jobs.get("fileToDbJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.start(fileToDbCheckTriggerFileStep)
				.next(fileToDbStep)
				.on(ExitStatus.COMPLETED.getExitCode()).to(fileToDbDeleteTriggerFileStep)
				.end()
				.build();
	}

	/** step configurations */
	@Bean
	public Step fileToDbCheckTriggerFileStep() {
		return steps.get("fileToDbCheckTriggerFileStep")
				.tasklet(fileToDbCheckTriggerFileTasklet())
				.build();
	}

	@Bean
	public Step fileToDbStep(SqlSessionFactory primarySqlSessionFactory, SampleStepExecutionListener stepExecutionListener) {
		return steps.get("importUserStep")
				.<PostCodeFileDto, PostCodeEntity> chunk(10)
				.reader(fileToDbItemReader())
				.processor(fileToDbItemProcessor())
				.writer(fileToDbItemWriter(primarySqlSessionFactory))
				.faultTolerant()
				.skipLimit(10)
				.skip(FlatFileParseException.class)
				.noSkip(IOException.class)
				.listener(stepExecutionListener)
				.build();
	}

	@Bean
	public Step fileToDbDeleteTriggerFileStep() {
		return steps.get("fileToDbDeleteTriggerFileStep")
				.tasklet(fileToDbDeleteTriggerFileTasklet())
				.build();
	}

	@Bean
	public TriggerFileTasklet fileToDbCheckTriggerFileTasklet() {
		TriggerFileTasklet tasklet = new TriggerFileTasklet();
		tasklet.setOperation(FileOperationVo.CHECK_DELETE);
		tasklet.setFilePath(triggerFilePath);
		tasklet.setFileName(triggerFileName);
		return tasklet;
	}

	@Bean
	public TriggerFileTasklet fileToDbDeleteTriggerFileTasklet() {
		TriggerFileTasklet tasklet = new TriggerFileTasklet();
		tasklet.setOperation(FileOperationVo.DELETE);
		tasklet.setFilePath(triggerFilePath);
		tasklet.setFileName(triggerFileName);
		return tasklet;
	}

	/** reader processor writer configurations */
	@Bean
	public FlatFileItemReader<PostCodeFileDto> fileToDbItemReader() {
		return new FlatFileItemReaderBuilder<PostCodeFileDto>()
				.name("fileToDbItemReader")
				.resource(new FileSystemResource(dataFilePath + "/" + dataFileName))
				.linesToSkip(1)
				.delimited()
				.delimiter(",")
				.names(PostCodeFileDto.FIELD)
				.encoding(EncodingCode.MS932)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PostCodeFileDto>() {
					{
						setTargetType(PostCodeFileDto.class);
					}
				})
				.build();
	}

	@Bean
	public PostCodeItemProcessor fileToDbItemProcessor() {
		return new PostCodeItemProcessor();
	}

	@Bean
	public MyBatisBatchItemWriter<PostCodeEntity> fileToDbItemWriter(SqlSessionFactory primarySqlSessionFactory) {
		MyBatisBatchItemWriter<PostCodeEntity> writer = new MyBatisBatchItemWriter<>();
		writer.setSqlSessionFactory(primarySqlSessionFactory);
		writer.setStatementId("jp.co.springbatch.sample.data.primary.repository.PostCodeRepository.insert");
		return writer;
	}

}
