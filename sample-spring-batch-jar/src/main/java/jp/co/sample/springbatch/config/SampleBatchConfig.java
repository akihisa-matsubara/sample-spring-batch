package jp.co.sample.springbatch.config;

import jp.co.sample.springbatch.framework.constant.BatchCommonConst;
import jp.co.sample.springbatch.framework.constant.ScopeConst;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * サンプルバッチ設定.
 * ・FW側のパッケージ読み込み
 */
@Scope(ScopeConst.SINGLETON)
@ComponentScan(basePackages = {BatchCommonConst.FW_PACKAGE})
@Configuration
public class SampleBatchConfig {
  // nothing
}
