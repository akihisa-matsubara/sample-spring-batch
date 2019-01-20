package jp.co.springbatch.sample.config.job;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
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
import jp.co.springbatch.sample.common.code.EncodingVo;
import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;
import jp.co.springbatch.sample.common.listener.SampleStepExecutionListener;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

@Scope(ScopeVo.SINGLETON)
@Configuration
@EnableBatchProcessing
public class FileToDbJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("${sample.file.file-to-db.path}")
	private String fileToDbPath;

	/** job configurations */
	@Bean
	public Job fileToDbJob(SampleJobExecutionListener jobExecutionListener,
			Step fileToDbStep) {
		return jobs.get("fileToDbJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.flow(fileToDbStep)
				.end()
				.build();
	}

	/** step configurations */
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

	/** reader processor writer configurations */
	@Bean
	public FlatFileItemReader<PostCodeFileDto> fileToDbItemReader() {
		return new FlatFileItemReaderBuilder<PostCodeFileDto>()
				.name("fileToDbItemReader")
				.resource(new FileSystemResource(fileToDbPath))
				.linesToSkip(1)
				.delimited()
				.delimiter(",")
				.names(PostCodeFileDto.FIELD)
				.encoding(EncodingVo.MS932)
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
