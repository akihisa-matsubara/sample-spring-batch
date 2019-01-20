package jp.co.springbatch.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Configuration
public class ChainedDbConfig {

	// Best Efforts 1PCパターン: 複数データソースをローカルトランザクションで扱い、同じタイミングで逐次コミットを発行
	@Bean
	public PlatformTransactionManager chainedTxManager(PlatformTransactionManager primaryTxManager,
			PlatformTransactionManager secondaryTxManager) {
		return new ChainedTransactionManager(primaryTxManager, secondaryTxManager);
	}

}
