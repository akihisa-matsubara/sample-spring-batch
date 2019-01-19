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

import jp.co.springbatch.sample.common.code.FileOperationVo;
import jp.co.springbatch.sample.common.util.SampleDateUtils;

public class TriggerFileTasklet implements Tasklet, InitializingBean {

	private FileOperationVo operation;
	private String filePath;
	private String fileName;

	@Autowired
	private SampleDateUtils dateUtils;

	public void setOperation(FileOperationVo operation) {
		this.operation = operation;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(operation, "operation must be set");
		Assert.notNull(filePath, "trigger file path must be set");
		Assert.notNull(fileName, "trigger file name must be set");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String systemDate = dateUtils.getNowDateString();
		String replaceFileName = fileName.replace("YYYYMMDD", systemDate);

		validate(replaceFileName);

		switch (operation) {
		case SAVE:
			Files.write(Paths.get(filePath, replaceFileName), systemDate.getBytes());
			break;

		case DELETE:
			Files.delete(Paths.get(filePath, replaceFileName));
			break;
		}

		return RepeatStatus.FINISHED;
	}

	private void validate(String targetFile) {
		Path path = Paths.get(filePath);
		Assert.isTrue(Files.isDirectory(path), "not a directory");

		switch(operation) {
		case SAVE:
			Assert.isTrue(!Files.exists(Paths.get(filePath, targetFile)), "trigger file exists");
			break;
		case DELETE:
			Assert.isTrue(Files.exists(Paths.get(filePath, targetFile)), "trigger file does not exists");
			break;
		}
	}

}
