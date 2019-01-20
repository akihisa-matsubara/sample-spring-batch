package jp.co.springbatch.sample.config.job;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;

import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;

@Scope(ScopeVo.SINGLETON)
@Configuration
@EnableBatchProcessing
public class FileToDbJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	/** job configurations */
	@Bean
	public Job fileToDbJob(JobExecutionListener listener, Step importUserStep) {
		return jobs.get("fileToDbJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(importUserStep)
				.end()
				.build();
	}

	/** step configurations */
	@Bean
	public Step importUserStep(JdbcBatchItemWriter<CustomerFamilyFileDto> writer) {
		return steps.get("importUserStep")
				.<CustomerFamilyFileDto, CustomerFamilyFileDto> chunk(10)
				.reader(reader())
//				.processor(processor())
				.writer(writer)
				.faultTolerant()
				.skipLimit(10)
				.skip(FlatFileParseException.class)
				.noSkip(IOException.class)
				.build();
	}

	/** reader processor writer configurations */
	@Bean
	public FlatFileItemReader<CustomerFamilyFileDto> reader() {
		return new FlatFileItemReaderBuilder<CustomerFamilyFileDto>()
				.name("customerItemReader")
				.resource(new ClassPathResource("customer-data.csv"))
				.delimited()
				.names(new String[] { "name", "address", "tel" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<CustomerFamilyFileDto>() {
					{
						setTargetType(CustomerFamilyFileDto.class);
					}
				})
				.build();
	}

//	@Bean
//	public CustomerItemProcessor processor() {
//		return new CustomerItemProcessor();
//	}

	@Bean
	public JdbcBatchItemWriter<CustomerFamilyFileDto> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<CustomerFamilyFileDto>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO TBS_CUSTOMER (NAME, ADDRESS, TEL) VALUES (:name, :address, :tel)")
				.dataSource(dataSource)
				.build();
	}
}
