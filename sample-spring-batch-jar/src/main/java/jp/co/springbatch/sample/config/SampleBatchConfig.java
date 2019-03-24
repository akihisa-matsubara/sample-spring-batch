package jp.co.springbatch.sample.config;

import jp.co.springbatch.framework.constant.BatchConst;
import jp.co.springbatch.framework.constant.ScopeConst;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * サンプルバッチ設定.
 * ・FW側のパッケージ読み込み
 */
@Scope(ScopeConst.SINGLETON)
@ComponentScan(basePackages = {BatchConst.FW_PACKAGE})
@Configuration
public class SampleBatchConfig {
  // nothing
}
