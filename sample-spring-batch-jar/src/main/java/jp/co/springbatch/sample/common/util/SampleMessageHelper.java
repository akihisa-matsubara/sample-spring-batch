package jp.co.springbatch.sample.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class SampleMessageHelper {
	@Autowired
	private MessageSource messageSource;

	public String message(SampleMessageId messageId, Object... param) {
		messageSource.getMessage(messageId., args, locale)
	}
}
