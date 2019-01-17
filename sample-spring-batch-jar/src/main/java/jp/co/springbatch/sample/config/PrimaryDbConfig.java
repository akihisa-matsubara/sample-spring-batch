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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = PrimaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDbConfig {
	public static final String BASE_PACKAGES = "jp.co.springbatch.sample.data.entity";
	public static final String MAPPER_XML_PATH = "classpath*:jp/co/springbatch/sample/data/entity/**/*.xml";

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource primaryDataSource(
			@Qualifier("primaryDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	public PlatformTransactionManager primaryTxManager(DataSource primaryDataSource) {
		return new DataSourceTransactionManager(primaryDataSource);
	}

	@Bean
	@Primary
	public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
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
