package jp.co.springbatch.sample.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.code.ScopeVo;
import jp.co.springbatch.sample.config.properties.SystemDateProperties;

@Scope(ScopeVo.SINGLETON)
@Component
public class SampleDateUtils {
	private static final SimpleDateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private SystemDateProperties systemDateProperties;

	public String getNowDateString() {
		String systemDate = systemDateProperties.getSystemDate();
		return StringUtils.isEmpty(systemDate) ? DATE_FORMAT_YYYYMMDD.format(new Date()) : systemDate;
	}

	public static String getDateString(Date date) {
		return date == null ? "" : DATE_FORMAT_YYYYMMDD.format(date);
	}
}
