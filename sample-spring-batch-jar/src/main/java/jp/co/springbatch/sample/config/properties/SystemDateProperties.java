package jp.co.springbatch.sample.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SystemDateProperties {

	@Value("${sample.system-date}")
    private String systemDate;

}