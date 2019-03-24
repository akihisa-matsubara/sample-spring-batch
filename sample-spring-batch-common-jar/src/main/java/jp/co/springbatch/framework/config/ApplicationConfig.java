package jp.co.springbatch.framework.config;

import jp.co.springbatch.framework.constant.ScopeConst;
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

/**
 * アプリケーション共通設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
public class ApplicationConfig {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

  /**
   * in-memoryジョブリポジトリ.
   *
   * @return {@link DefaultBatchConfigurer} バッチ設定
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
          if (jobRepository == null) {
            throw new IllegalStateException("jobRepository is null.");
          }
          MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
          this.jobExplorer = jobExplorerFactory.getObject();
          SimpleJobLauncher launcher = new SimpleJobLauncher();
          launcher.setJobRepository(jobRepository);
          launcher.afterPropertiesSet();
          this.jobLauncher = launcher;

        } catch (Exception e) {
          LOGGER.error(ExceptionUtils.getStackTrace(e));

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
