package dev.sample.springbatch.framework.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import dev.sample.springbatch.framework.constant.ScopeConst;

/**
 * アプリケーション共通設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
public class ApplicationConfig {

  /**
   * in-memoryジョブリポジトリ.
   *
   * @return {@link DefaultBatchConfigurer} バッチ設定
   */
  @Bean
  DefaultBatchConfigurer batchConfigurer() {
    return new JobRepositoryConfig();
  }

}
