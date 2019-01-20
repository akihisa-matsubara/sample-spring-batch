package jp.co.springbatch.sample.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.config.properties.SystemDateProperties;
import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Component
public class SampleDateUtils {

	private static final SimpleDateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat DATE_FORMAT_HHMMSS = new SimpleDateFormat("HHmmssSSS");
	private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateTimeFormatter DATE_TIME_FOMAT_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private static final Logger log = LoggerFactory.getLogger(SampleDateUtils.class);

	@Autowired
	private SystemDateProperties systemDateProperties;

	public String getNowDateString() {
		String systemDate = systemDateProperties.getSystemDate();
		return StringUtils.isEmpty(systemDate) ? DATE_FORMAT_YYYYMMDD.format(new Date()) : systemDate;
	}

	public LocalDateTime getNowLocalDateTime() {
		String systemDate = getNowDateString() + DATE_FORMAT_HHMMSS.format(new Date());
		return LocalDateTime.parse(systemDate, DATE_TIME_FOMAT_YYYYMMDDHHMMSS);
	}

	public static String getDateString(Date date) {
		return date == null ? "" : DATE_FORMAT_YYYYMMDD.format(date);
	}

	public static String getDateTimeString(Date date) {
		return date == null ? "" : DATE_FORMAT_DATETIME.format(date);
	}

	public static Date parseDate(String date) {
		try {
			return DATE_FORMAT_YYYYMMDD.parse(date);

		} catch (ParseException pe) {
			log.error(ExceptionUtils.getStackTrace(pe));
			throw new RuntimeException(pe);

		}
	}
}
