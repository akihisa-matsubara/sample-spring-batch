package dev.sample.springbatch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import dev.sample.springbatch.framework.constant.BatchCommonConst;
import dev.sample.springbatch.framework.constant.ScopeConst;

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
