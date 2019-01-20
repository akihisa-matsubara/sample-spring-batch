package jp.co.springbatch.sample.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.code.ScopeVo;
import lombok.Getter;
import lombok.Setter;

// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Scope(ScopeVo.SINGLETON)
@Component
@Getter
@Setter
public class SystemDateProperties {

	@Value("${sample.system-date}")
	private String systemDate;

}
