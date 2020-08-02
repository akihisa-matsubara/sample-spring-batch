package dev.sample.springbatch.framework.config.database;

import dev.sample.springbatch.framework.constant.ScopeConst;
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
 * FW DB用設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@MapperScan(basePackages = CoreDbConfig.BASE_PACKAGES, sqlSessionTemplateRef = "coreSqlSessionTemplate")
public class CoreDbConfig {

  /** basePackages. */
  public static final String BASE_PACKAGES = "dev.sample.springbatch.framework.data.core.repository";

  /** MapperXmlパス. */
  public static final String MAPPER_XML_PATH = "classpath*:jp/co/sample/springbatch/framework/data/core/repository/*.xml";

  /**
   * FW DB用DataSourceProperties.
   *
   * @return {@link DataSourceProperties} FW DB用DataSourceProperties
   */
  @Bean
  @ConfigurationProperties(prefix = "framework.datasource.core")
  public DataSourceProperties coreDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * FW DB用HikariDataSource.
   *
   * @return {@link HikariDataSource} FW DB用HikariDataSource
   */
  @Bean
  public HikariDataSource coreDataSource() {
    return coreDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  /**
   * FW DB用TransactionManager.
   *
   * @param coreDataSource {@link DataSource} FW DB用HikariDataSource
   * @return {@link PlatformTransactionManager} FW DB用TransactionManager
   */
  @Bean
  public PlatformTransactionManager coreTxManager(DataSource coreDataSource) {
    return new DataSourceTransactionManager(coreDataSource);
  }

  /**
   * FW DB用SqlSessionFactory.
   *
   * @param coreDataSource {@link DataSource} FW DB用HikariDataSource
   * @return {@link SqlSessionFactory} FW DB用SqlSessionFactory
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionFactory coreSqlSessionFactory(DataSource coreDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(coreDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
    return bean.getObject();
  }

  /**
   * FW DB用SqlSessionTemplate.
   *
   * @param coreSqlSessionFactory {@link SqlSessionFactory} FW DB用SqlSessionFactory
   * @return {@link SqlSessionTemplate} FW DB用SqlSessionTemplate
   */
  @Bean
  public SqlSessionTemplate coreSqlSessionTemplate(@Qualifier("coreSqlSessionFactory") SqlSessionFactory coreSqlSessionFactory) {
    return new SqlSessionTemplate(coreSqlSessionFactory, ExecutorType.BATCH);
  }

}
