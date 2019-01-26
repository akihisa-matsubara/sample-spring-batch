package jp.co.springbatch.sample.config.database;

import jp.co.springbatch.sample.common.constant.ScopeConst;

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
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 副DB用設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@MapperScan(basePackages = SecondaryDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryDbConfig {

  /** basePackages. */
  public static final String BASE_PACKAGES = "jp.co.springbatch.sample.data.secondary.repository";

  /** MapperXmlパス. */
  public static final String MAPPER_XML_PATH = "classpath*:jp/co/springbatch/sample/data/secondary/repository/*.xml";

  /**
   * 副DB用DataSourceProperties.
   *
   * @return DataSourceProperties 副DB用DataSourceProperties
   */
  @Bean
  @ConfigurationProperties(prefix = "sample.datasource.secondary")
  public DataSourceProperties secondaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * 副DB用HikariDataSource.
   *
   * @return HikariDataSource 副DB用HikariDataSource
   */
  @Bean
  public HikariDataSource secondaryDataSource() {
    return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  /**
   * 副DB用TransactionManager.
   *
   * @param secondaryDataSource 副DB用HikariDataSource
   * @return PlatformTransactionManager 副DB用TransactionManager
   */
  @Bean
  public PlatformTransactionManager secondaryTxManager(DataSource secondaryDataSource) {
    return new DataSourceTransactionManager(secondaryDataSource);
  }

  /**
   * 副DB用SqlSessionFactory.
   *
   * @param secondaryDataSource 副DB用HikariDataSource
   * @return SqlSessionFactory 副DB用SqlSessionFactory
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionFactory secondarySqlSessionFactory(DataSource secondaryDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(secondaryDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
    return (SqlSessionFactory) bean.getObject();
  }

  /**
   * 副DB用SqlSessionTemplate.
   *
   * @param secondarySqlSessionFactory 副DB用SqlSessionFactory
   * @return SqlSessionTemplate 副DB用SqlSessionTemplate
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionTemplate secondarySqlSessionTemplate(
      @Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(secondarySqlSessionFactory, ExecutorType.BATCH);
  }

}
