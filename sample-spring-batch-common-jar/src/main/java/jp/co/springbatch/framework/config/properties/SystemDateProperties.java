package jp.co.springbatch.framework.config.properties;

import jp.co.springbatch.framework.constant.ScopeConst;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * システム日付プロパティ.
 * テストで任意の日付で動作させたい場合に利用
 */
@Scope(ScopeConst.SINGLETON)
@Component
@Getter
public class SystemDateProperties {

  /**
   * システム日付（みなし日付）.
   * ※起動時に読み込まれた後は利用しません.
   */
  @Value("${framework.system-date}")
  private String systemDate;

}
