package dev.sample.springbatch.test.context;

import dev.sample.common.constant.Profile;
import dev.sample.springbatch.config.SampleBatchConfig;
import dev.sample.springbatch.constant.BatchConst;
import dev.sample.springbatch.framework.constant.BatchCommonConst;
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
 * {@code @ComponentScan} では基底パッケージ(Application)とJOB用Configを読み込まないこと.
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
    BatchCommonConst.FW_PACKAGE,
    BatchConst.BIZ_PACKAGE,
    BatchConst.DB_CONFIG_PACKAGE,
    BatchConst.DATA_PACKAGE,
    BatchConst.INTEGRATION_PACKAGE
})
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(Profile.UT)
public @interface SampleSpringBatchTest {
}
