package jp.co.springbatch.sample.biz.tasklet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import jp.co.springbatch.sample.common.util.SampleDateUtils;

public class OutputTriggerFileTasklet implements Tasklet, InitializingBean {

	private String filePath;
	private String fileName;

	@Autowired
	private SampleDateUtils dateUtils;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Path path = Paths.get(filePath);
        Assert.isTrue(Files.isDirectory(path), "not a directory");

        String systemDate = dateUtils.getNowDateString();
        String replaceFileName = fileName.replace("YYYYMMDD", systemDate);
        Assert.isTrue(!Files.exists(Paths.get(filePath, replaceFileName)), "exists a trigger file");

        Files.write(Paths.get(filePath, replaceFileName), systemDate.getBytes());

		return RepeatStatus.FINISHED;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(filePath, "trigger file path must be set");
		Assert.notNull(fileName, "trigger file name must be set");
	}

}
