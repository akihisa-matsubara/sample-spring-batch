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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import jp.co.springbatch.sample.common.constant.ScopeCode;

@Scope(ScopeCode.SINGLETON)
@Configuration
@MapperScan(basePackages = PrimaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDbConfig {

	public static final String BASE_PACKAGES = "jp.co.springbatch.sample.data.primary.repository";
	public static final String MAPPER_XML_PATH = "classpath*:jp/co/springbatch/sample/data/primary/repository/*.xml";

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "sample.datasource.primary")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public HikariDataSource primaryDataSource() {
		return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	public PlatformTransactionManager primaryTxManager(DataSource primaryDataSource) {
		return new DataSourceTransactionManager(primaryDataSource);
	}

	@Bean
	public SqlSessionFactory primarySqlSessionFactory(DataSource primaryDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(primaryDataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
		return (SqlSessionFactory) bean.getObject();
	}

	@Bean
	public SqlSessionTemplate primarySqlSessionTemplate(
			@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(primarySqlSessionFactory, ExecutorType.BATCH);
	}

}
