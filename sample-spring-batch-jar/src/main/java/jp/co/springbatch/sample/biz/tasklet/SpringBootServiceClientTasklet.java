package jp.co.springbatch.sample.biz.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.integration.service.SpringBootService;

@Scope(ScopeCode.SINGLETON)
@Component
public class SpringBootServiceClientTasklet implements Tasklet {

	@Autowired
	private SpringBootService service;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		service.getRandomQuotation();

		return RepeatStatus.FINISHED;
	}

}
