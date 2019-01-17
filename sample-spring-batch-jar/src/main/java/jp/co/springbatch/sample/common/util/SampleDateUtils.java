package jp.co.springbatch.sample.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jp.co.springbatch.sample.config.properties.SystemDateProperties;

@Component
public class SampleDateUtils {
	private static final SimpleDateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private SystemDateProperties systemDateProperties;

	public String getNowDateString() {
		String systemDate = systemDateProperties.getSystemDate();
		return StringUtils.isEmpty(systemDate) ? DATE_FORMAT_YYYYMMDD.format(new Date()) : systemDate;
	}
}
