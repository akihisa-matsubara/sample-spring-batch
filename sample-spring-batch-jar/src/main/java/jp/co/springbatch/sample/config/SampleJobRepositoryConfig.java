package jp.co.springbatch.sample.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Configuration
public class SampleJobRepositoryConfig {

	/**
	 * in-memoryジョブリポジトリを使用する
	 *
	 * @return
	 */
	@Bean
	DefaultBatchConfigurer batchConfigurer() {
		return new DefaultBatchConfigurer() {

			private JobRepository jobRepository;
			private JobExplorer jobExplorer;
			private JobLauncher jobLauncher;

			{
				MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean();
				try {
					this.jobRepository = jobRepositoryFactory.getObject();
					MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
					this.jobExplorer = jobExplorerFactory.getObject();
					SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
					jobLauncher.setJobRepository(jobRepository);
					jobLauncher.afterPropertiesSet();
					this.jobLauncher = jobLauncher;

				} catch (Exception e) {
				}
			}

			@Override
			public JobRepository getJobRepository() {
				return jobRepository;
			}

			@Override
			public JobExplorer getJobExplorer() {
				return jobExplorer;
			}

			@Override
			public JobLauncher getJobLauncher() {
				return jobLauncher;
			}
		};
	}

}
