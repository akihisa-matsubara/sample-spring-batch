package jp.co.sample.springbatch.framework.util;

import jp.co.sample.framework.util.AbstractSystemDateUtils;
import jp.co.sample.springbatch.framework.config.properties.SystemDateProperties;
import jp.co.sample.springbatch.framework.constant.ScopeConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * システム日付ユーティリティー.
 * システム日付を変更したい場合は、application.ymlに
 * framework.system-date: yyyyMMdd形式で設定してください.
 */
@Scope(ScopeConst.SINGLETON)
@Component
public class SystemDateUtils extends AbstractSystemDateUtils {

  /**
   * デフォルトコンストラクタ.
   *
   * @param systemDateProperties システム日付プロパティ
   */
  @Autowired
  private SystemDateUtils(SystemDateProperties systemDateProperties) {
    setSystemDate(systemDateProperties.getSystemDate());
  }

}
