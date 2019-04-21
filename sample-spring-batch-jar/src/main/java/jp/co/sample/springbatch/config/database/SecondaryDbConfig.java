package jp.co.sample.springbatch.config.database;

import jp.co.sample.springbatch.framework.constant.ScopeConst;
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
  public static final String BASE_PACKAGES = "jp.co.sample.springbatch.data.secondary.repository";

  /** MapperXmlパス. */
  public static final String MAPPER_XML_PATH = "classpath*:jp/co/sample/springbatch/data/secondary/repository/*.xml";

  /**
   * 副DB用DataSourceProperties.
   *
   * @return {@link DataSourceProperties} 副DB用DataSourceProperties
   */
  @Bean
  @ConfigurationProperties(prefix = "sample.datasource.secondary")
  public DataSourceProperties secondaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  /**
   * 副DB用HikariDataSource.
   *
   * @return {@link HikariDataSource} 副DB用HikariDataSource
   */
  @Bean
  public HikariDataSource secondaryDataSource() {
    return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  /**
   * 副DB用TransactionManager.
   *
   * @param secondaryDataSource {@link DataSource} 副DB用HikariDataSource
   * @return {@link PlatformTransactionManager} 副DB用TransactionManager
   */
  @Bean
  public PlatformTransactionManager secondaryTxManager(DataSource secondaryDataSource) {
    return new DataSourceTransactionManager(secondaryDataSource);
  }

  /**
   * 副DB用SqlSessionFactory.
   *
   * @param secondaryDataSource {@link DataSource} 副DB用HikariDataSource
   * @return {@link SqlSessionFactory} 副DB用SqlSessionFactory
   * @throws Exception 例外
   */
  @Bean
  public SqlSessionFactory secondarySqlSessionFactory(DataSource secondaryDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(secondaryDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
    return bean.getObject();
  }

  /**
   * 副DB用SqlSessionTemplate.
   *
   * @param secondarySqlSessionFactory {@link SqlSessionFactory} 副DB用SqlSessionFactory
   * @return {@link SqlSessionTemplate} 副DB用SqlSessionTemplate
   */
  @Bean
  public SqlSessionTemplate secondarySqlSessionTemplate(
      @Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) {
    return new SqlSessionTemplate(secondarySqlSessionFactory, ExecutorType.BATCH);
  }

}
