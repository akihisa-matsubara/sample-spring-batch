package dev.sample.springbatch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import dev.sample.springbatch.biz.tasklet.SampleRestServiceClientTasklet;
import dev.sample.springbatch.biz.tasklet.SpringBootServiceClientTasklet;
import dev.sample.springbatch.framework.constant.ScopeConst;
import dev.sample.springbatch.framework.listener.SampleJobExecutionListener;

/**
 * Rest Service呼び出しジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CallRestServiceJobConfig {

  /** StepBuilderFactory. (Constructor Injection) */
  private final StepBuilderFactory steps;

  /**********************************************
   * job configurations.
   **********************************************/
  /**
   * Rest Service呼び出しジョブ.
   *
   * @param jobs {@link JobBuilderFactory}
   * @param jobExecutionListener {@link SampleJobExecutionListener} ジョブ実行リスナー
   * @param callSpringBootServiceStep Spring Boot Service呼び出しステップ
   * @param callSampleRestServiceStep Sample Rest Service呼び出しステップ
   * @return {@link Job} ジョブ
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
   * @param callSpringBootServiceTasklet {@link SpringBootServiceClientTasklet} Spring Boot Service Client
   * @return {@link Step} Spring Boot Service呼び出しステップ
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
   * @param callSampleRestServiceTasklet {@link SampleRestServiceClientTasklet} Sample Rest Service Client
   * @return {@link Step} Sample Rest Service呼び出しステップ
   */
  @Bean
  public Step callSampleRestServiceStep(SampleRestServiceClientTasklet callSampleRestServiceTasklet) {
    return steps.get("callSampleRestServiceStep")
        .tasklet(callSampleRestServiceTasklet)
        .build();
  }
}
