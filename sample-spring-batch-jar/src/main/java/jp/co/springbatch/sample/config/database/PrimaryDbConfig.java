package jp.co.springbatch.sample.config.database;

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

/**
 * 主DB用設定.
 */
@Scope(ScopeCode.SINGLETON)
@Configuration
@MapperScan(basePackages = PrimaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDbConfig {

  /** basePackages. */
  public static final String BASE_PACKAGES = "jp.co.springbatch.sample.data.primary.repository";

  /** MapperXmlパス. */
  public static final String MAPPER_XML_PATH = "classpath*:jp/co/springbatch/sample/data/primary/repository/*.xml";

  /**
   * 主DB用DataSourceProperties.
   *
   * @return DataSourceProperties 主DB用DataSourceProperties
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
   * @return HikariDataSource 主DB用HikariDataSource
   */
  @Bean
  @Primary
  public HikariDataSource primaryDataSource() {
    return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  /**
   * 主DB用TransactionManager.
   *
   * @return PlatformTransactionManager 主DB用TransactionManager
   */
  @Bean
  public PlatformTransactionManager primaryTxManager(DataSource primaryDataSource) {
    return new DataSourceTransactionManager(primaryDataSource);
  }

  /**
   * 主DB用SqlSessionFactory.
   *
   * @param primaryDataSource 主DB用HikariDataSource
   * @return SqlSessionFactory 主DB用SqlSessionFactory
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionFactory primarySqlSessionFactory(DataSource primaryDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(primaryDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
    return (SqlSessionFactory) bean.getObject();
  }

  /**
   * 主DB用SqlSessionTemplate.
   *
   * @param primarySqlSessionFactory 主DB用SqlSessionFactory
   * @return SqlSessionTemplate 主DB用SqlSessionTemplate
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory)
      throws Exception {
    return new SqlSessionTemplate(primarySqlSessionFactory, ExecutorType.BATCH);
  }

}
