package jp.co.springbatch.sample.config.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import jp.co.springbatch.sample.biz.tasklet.SampleRestServiceClientTasklet;
import jp.co.springbatch.sample.biz.tasklet.SpringBootServiceClientTasklet;
import jp.co.springbatch.sample.common.constant.ScopeConst;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;

/**
 * Rest Service呼び出しジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
public class CallRestServiceJobConfig {

  /** JobBuilderFactory. */
  @Autowired
  private JobBuilderFactory jobs;

  /** StepBuilderFactory. */
  @Autowired
  private StepBuilderFactory steps;

  /** Spring Boot Service Client. */
  @Autowired
  private SpringBootServiceClientTasklet callSpringBootServiceTasklet;

  /** Sample Rest Service Client. */
  @Autowired
  private SampleRestServiceClientTasklet callSampleRestServiceTasklet;

  /**********************************************
   * job configurations.
   **********************************************/
  /**
   * Rest Service呼び出しジョブ.
   *
   * @param jobExecutionListener ジョブ実行リスナー
   * @param callSpringBootServiceStep Spring Boot Service呼び出しステップ
   * @param callSampleRestServiceStep Sample Rest Service呼び出しステップ
   * @return Job ジョブ
   * @throws Exception 例外
   */
  @Bean
  public Job callRestServiceJob(SampleJobExecutionListener jobExecutionListener,
      Step callSpringBootServiceStep,
      Step callSampleRestServiceStep) throws Exception {
    return jobs.get("callRestServiceJob")
        .incrementer(new RunIdIncrementer())
        .listener(jobExecutionListener)
        .start(callSpringBootServiceStep)
        .next(callSampleRestServiceStep)
        .build();
  }

  /**********************************************
   * step configurations.
   **********************************************/
  /**
   * Spring Boot Service呼び出しステップ.
   *
   * @return Step Spring Boot Service呼び出しステップ
   */
  @Bean
  public Step callSpringBootServiceStep() {
    return steps.get("callSpringBootServiceStep")
        .tasklet(callSpringBootServiceTasklet)
        .build();
  }

  /**
   * Sample Rest Service呼び出しステップ.
   *
   * @return Step Sample Rest Service呼び出しステップ
   */
  @Bean
  public Step callSampleRestServiceStep() {
    return steps.get("callSampleRestServiceStep")
        .tasklet(callSampleRestServiceTasklet)
        .build();
  }
}
