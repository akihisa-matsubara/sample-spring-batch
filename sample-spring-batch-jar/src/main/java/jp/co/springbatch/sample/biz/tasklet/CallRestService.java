package jp.co.springbatch.sample.biz.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jp.co.springbatch.sample.data.dto.Quote;

@Component
public class CallRestService implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(CallRestService.class);

	private final RestTemplate restTemplate;

	public CallRestService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Call Rest Service Logic..."); // サンプルでは未実装
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		log.info(quote.toString());
		return RepeatStatus.FINISHED;
	}

}
