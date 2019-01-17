package jp.co.springbatch.sample.biz.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.data.entity.primary.TbsCustomer;
import jp.co.springbatch.sample.data.entity.primary.TbsCustomerMapper;

@Component
public class OutputLogTasklet implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(OutputLogTasklet.class);

	@Autowired
	private TbsCustomerMapper tbsCustomerMapper;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Output Log Logic..."); // サンプルでは未実装
		TbsCustomer result = tbsCustomerMapper.selectByPrimaryKey(3);
		log.info("select result: " + result);

		if (isCheckOK()) {
			contribution.setExitStatus(ExitStatus.COMPLETED);
		} else {
			contribution.setExitStatus(ExitStatus.FAILED);
		}

		return RepeatStatus.FINISHED;
	}

	private boolean isCheckOK() {
		return true;
	}

}
