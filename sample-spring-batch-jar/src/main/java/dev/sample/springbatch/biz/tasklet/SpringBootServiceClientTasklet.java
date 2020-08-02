package dev.sample.springbatch.biz.tasklet;

import dev.sample.springbatch.framework.constant.ScopeConst;
import dev.sample.springbatch.integration.service.SpringBootService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Spring Boot Service Client.
 */
@Scope(ScopeConst.SINGLETON)
@Service
@RequiredArgsConstructor
public class SpringBootServiceClientTasklet implements Tasklet {

  /** Spring Boot Service. (Constructor Injection) */
  private final SpringBootService service;

  /**
   * 実行.
   * API:/random, Get
   *
   * @param contribution {@link StepContribution}
   * @param chunkContext {@link ChunkContext}
   * @return {@link RepeatStatus} 結果ステータス
   * @throws Exception 例外
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    service.getRandomQuotation();

    return RepeatStatus.FINISHED;
  }

}
