package dev.sample.springbatch.config.database;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
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
import dev.sample.springbatch.framework.constant.ScopeConst;

/**
 * 主DB用設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@MapperScan(basePackages = PrimaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDbConfig {

  /** basePackages. */
  public static final String BASE_PACKAGES = "dev.sample.springbatch.data.primary.repository";

  /** MapperXmlパス. */
  public static final String MAPPER_XML_PATH = "classpath*:jp/co/sample/springbatch/data/primary/repository/*.xml";

  /**
   * 主DB用DataSourceProperties.
   *
   * @return {@link DataSourceProperties} 主DB用DataSourceProperties
   */
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "sample.datasource.primary")
  public DataSourceProperties primaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * 主DB用HikariDataSource.
   *
   * @return {@link HikariDataSource} 主DB用HikariDataSource
   */
  @Bean
  @Primary
  public HikariDataSource primaryDataSource() {
    return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  /**
   * 主DB用TransactionManager.
   *
   * @param primaryDataSource {@link DataSource} 主DB用HikariDataSource
   * @return {@link PlatformTransactionManager} 主DB用TransactionManager
   */
  @Bean
  public PlatformTransactionManager primaryTxManager(DataSource primaryDataSource) {
    return new DataSourceTransactionManager(primaryDataSource);
  }

  /**
   * 主DB用SqlSessionFactory.
   *
   * @param primaryDataSource {@link DataSource} 主DB用HikariDataSource
   * @return {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionFactory primarySqlSessionFactory(DataSource primaryDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(primaryDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
    return bean.getObject();
  }

  /**
   * 主DB用SqlSessionTemplate.
   *
   * @param primarySqlSessionFactory {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @return {@link SqlSessionTemplate} 主DB用SqlSessionTemplate
   */
  @Bean
  public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) {
    return new SqlSessionTemplate(primarySqlSessionFactory, ExecutorType.BATCH);
  }

}
