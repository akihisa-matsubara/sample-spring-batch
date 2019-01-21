package jp.co.springbatch.sample.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * サンプルバッチ設定.
 */
@Scope(ScopeCode.SINGLETON)
@Configuration
public class SampleBatchConfig {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleBatchConfig.class);

  /**
   * in-memoryジョブリポジトリ.
   *
   * @return DefaultBatchConfigurer バッチ設定
   */
  @Bean
  DefaultBatchConfigurer batchConfigurer() {
    return new DefaultBatchConfigurer() {

      /** ジョブリポジトリー. */
      private JobRepository jobRepository;

      /** ジョブエクスプローラー. */
      private JobExplorer jobExplorer;

      /** ジョブランチャー. */
      private JobLauncher jobLauncher;

      {
        // TransactionManager: ResourcelessTransactionManager
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
          log.error(ExceptionUtils.getStackTrace(e));

        }
      }

      /**
       * ジョブリポジトリーを取得します.
       *
       * @param JobRepository ジョブリポジトリー
       */
      @Override
      public JobRepository getJobRepository() {
        return jobRepository;
      }

      /**
       * ジョブエクスプローラーを取得します.
       *
       * @param JobExplorer ジョブエクスプローラー
       */
      @Override
      public JobExplorer getJobExplorer() {
        return jobExplorer;
      }

      /**
       * ジョブランチャーを取得します.
       *
       * @param JobLauncher ジョブランチャー
       */
      @Override
      public JobLauncher getJobLauncher() {
        return jobLauncher;
      }
    };
  }

}
