package jp.co.springbatch.sample.config.job;

import jp.co.springbatch.sample.biz.tasklet.SampleRestServiceClientTasklet;
import jp.co.springbatch.sample.biz.tasklet.SpringBootServiceClientTasklet;
import jp.co.springbatch.sample.common.constant.ScopeConst;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;
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

/**
 * Rest Service呼び出しジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
public class CallRestServiceJobConfig {

  /** StepBuilderFactory. */
  @Autowired
  private StepBuilderFactory steps;

  /**********************************************
   * job configurations.
   **********************************************/
  /**
   * Rest Service呼び出しジョブ.
   *
   * @param jobs JobBuilderFactory
   * @param jobExecutionListener ジョブ実行リスナー
   * @param callSpringBootServiceStep Spring Boot Service呼び出しステップ
   * @param callSampleRestServiceStep Sample Rest Service呼び出しステップ
   * @return Job ジョブ
   */
  @Bean
  public Job callRestServiceJob(JobBuilderFactory jobs,
      SampleJobExecutionListener jobExecutionListener,
      Step callSpringBootServiceStep,
      Step callSampleRestServiceStep) {
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
   * @param callSpringBootServiceTasklet Spring Boot Service Client
   * @return Step Spring Boot Service呼び出しステップ
   */
  @Bean
  public Step callSpringBootServiceStep(SpringBootServiceClientTasklet callSpringBootServiceTasklet) {
    return steps.get("callSpringBootServiceStep")
        .tasklet(callSpringBootServiceTasklet)
        .build();
  }

  /**
   * Sample Rest Service呼び出しステップ.
   *
   * @param callSampleRestServiceTasklet Sample Rest Service Client
   * @return Step Sample Rest Service呼び出しステップ
   */
  @Bean
  public Step callSampleRestServiceStep(SampleRestServiceClientTasklet callSampleRestServiceTasklet) {
    return steps.get("callSampleRestServiceStep")
        .tasklet(callSampleRestServiceTasklet)
        .build();
  }
}
