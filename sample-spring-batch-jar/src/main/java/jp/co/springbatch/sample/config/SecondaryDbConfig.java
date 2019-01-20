package jp.co.springbatch.sample.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Configuration
@MapperScan(basePackages = SecondaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryDbConfig {

	public static final String BASE_PACKAGES = "jp.co.springbatch.sample.data.secondary.repository";
	public static final String MAPPER_XML_PATH = "classpath*:jp/co/springbatch/sample/data/secondary/repository/*.xml";

	@Bean
	@ConfigurationProperties(prefix = "sample.datasource.secondary")
	public DataSourceProperties secondaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public HikariDataSource secondaryDataSource() {
		return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	public PlatformTransactionManager secondaryTxManager(DataSource secondaryDataSource) {
		return new DataSourceTransactionManager(secondaryDataSource);
	}

	@Bean
	public SqlSessionFactory secondarySqlSessionFactory(DataSource secondaryDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(secondaryDataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
		return (SqlSessionFactory) bean.getObject();
	}

	@Bean
	public SqlSessionTemplate secondarySqlSessionTemplate(
			@Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(secondarySqlSessionFactory, ExecutorType.BATCH);
	}

}
