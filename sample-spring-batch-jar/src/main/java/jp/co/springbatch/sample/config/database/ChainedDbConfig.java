package jp.co.springbatch.sample.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import jp.co.springbatch.sample.common.constant.ScopeCode;

/**
 * 複数DB用設定.
 */
@Scope(ScopeCode.SINGLETON)
@Configuration
public class ChainedDbConfig {

  /**
   * 複数データソース用TransactionManager.
   * Best Efforts 1PCパターン: 複数データソースをローカルトランザクションで扱い、同じタイミングで逐次コミットを発行
   *
   * @param primaryTxManager 主TransactionManager
   * @param secondaryTxManager 副TransactionManager
   * @return PlatformTransactionManager TransactionManager
   */
  @Bean
  public PlatformTransactionManager chainedTxManager(PlatformTransactionManager primaryTxManager,
      PlatformTransactionManager secondaryTxManager) {
    return new ChainedTransactionManager(primaryTxManager, secondaryTxManager);
  }

}
