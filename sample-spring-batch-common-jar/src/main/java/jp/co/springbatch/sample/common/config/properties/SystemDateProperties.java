package jp.co.springbatch.sample.common.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jp.co.springbatch.sample.common.constant.ScopeCode;
import lombok.Getter;
import lombok.Setter;

/**
 * システム日付プロパティ.
 * テストで任意の日付で動作させたい場合に利用
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Scope(ScopeCode.SINGLETON)
@Component
@Getter
@Setter
public class SystemDateProperties {

  /**
   * システム日付（みなし日付）.
   */
  @Value("${sample.common.system-date}")
  private String systemDate;

}
