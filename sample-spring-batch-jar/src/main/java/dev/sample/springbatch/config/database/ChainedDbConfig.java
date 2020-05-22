package dev.sample.springbatch.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import dev.sample.springbatch.framework.constant.ScopeConst;

/**
 * 複数DB用設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
public class ChainedDbConfig {

  /**
   * 複数データソース用TransactionManager.
   * Best Efforts 1PCパターン: 複数データソースをローカルトランザクションで扱い、同じタイミングで逐次コミットを発行
   *
   * @param primaryTxManager 主{@link TransactionManager}
   * @param secondaryTxManager 副{@link TransactionManager}
   * @return {@link PlatformTransactionManager} TransactionManager
   */
  @Bean
  public PlatformTransactionManager chainedTxManager(PlatformTransactionManager primaryTxManager,
      PlatformTransactionManager secondaryTxManager) {
    return new ChainedTransactionManager(primaryTxManager, secondaryTxManager);
  }

}
