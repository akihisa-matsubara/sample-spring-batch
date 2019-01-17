package jp.co.springbatch.sample.biz.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.integration.service.RandomSpringBootQuotationService;

@Component
public class CallRestService implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(CallRestService.class);

	@Autowired
	private RandomSpringBootQuotationService service;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Call Rest Service Logic..."); // サンプルでは未実装
		service.getQuotation();
		return RepeatStatus.FINISHED;
	}

}
