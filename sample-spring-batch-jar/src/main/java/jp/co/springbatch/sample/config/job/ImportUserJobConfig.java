package jp.co.springbatch.sample.config.job;

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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jp.co.springbatch.sample.biz.processor.CustomerItemProcessor;
import jp.co.springbatch.sample.common.listener.JobExecutionListener;
import jp.co.springbatch.sample.data.dto.Customer;

@Configuration
@EnableBatchProcessing
public class ImportUserJobConfig {

	@Autowired
	public JobBuilderFactory jobs;

	@Autowired
	public StepBuilderFactory steps;

	// tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<Customer> reader() {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.resource(new ClassPathResource("customer-data.csv"))
				.delimited()
				.names(new String[] { "name", "address", "tel" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
					{
						setTargetType(Customer.class);
					}
				})
				.build();
	}

	@Bean
	public CustomerItemProcessor processor() {
		return new CustomerItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Customer>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO TBS_CUSTOMER (NAME, ADDRESS, TEL) VALUES (:name, :address, :tel)")
				.dataSource(dataSource)
				.build();
	}
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobExecutionListener listener, Step importUserStep) {
		return jobs.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(importUserStep)
				.end()
				.build();
	}

	@Bean
	public Step importUserStep(JdbcBatchItemWriter<Customer> writer) {
		return steps.get("importUserStep")
				.<Customer, Customer> chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.build();
	}
	// end::jobstep[]
}
