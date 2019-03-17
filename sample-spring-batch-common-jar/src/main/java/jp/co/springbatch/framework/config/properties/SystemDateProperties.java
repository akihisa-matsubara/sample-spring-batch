package jp.co.springbatch.framework.config.properties;

import jp.co.springbatch.framework.constant.ScopeConst;
import lombok.Getter;
import lombok.Setter;
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
@Setter
public class SystemDateProperties {

  /**
   * システム日付（みなし日付）.
   */
  @Value("${framework.system-date}")
  private String systemDate;

}
