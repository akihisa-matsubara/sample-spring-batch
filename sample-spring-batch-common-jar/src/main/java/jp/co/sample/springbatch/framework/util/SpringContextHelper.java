package jp.co.sample.springbatch.framework.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context Helper.
 */
@Component
public class SpringContextHelper implements ApplicationContextAware {

  /** Application Context. */
  private static ApplicationContext applicationContext;

  /**
   * Beanを取得します.
   *
   * @param <T> beanクラス
   * @param clazz beanクラス
   * @return beanインスタンス
   */
  public static <T> T getBean(Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setApplicationContext(ApplicationContext context) {
    applicationContext = context;
  }

}
