package jp.co.springbatch.test.context;

import jp.co.springbatch.framework.constant.BatchConst;
import jp.co.springbatch.framework.constant.Profile;
import jp.co.springbatch.sample.config.SampleBatchConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * サンプルテスト用アノテーション.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@SpringBatchTest
@ContextConfiguration(
    classes = {SampleBatchConfig.class},
    initializers = ConfigFileApplicationContextInitializer.class)
@ComponentScan(basePackages = {
    BatchConst.FW_PACKAGE,
    "jp.co.springbatch.sample.biz",
    "jp.co.springbatch.sample.config.database",
    "jp.co.springbatch.sample.data",
    "jp.co.springbatch.sample.integration"
})
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(Profile.TEST)
public @interface SampleSpringBatchTest {
}
