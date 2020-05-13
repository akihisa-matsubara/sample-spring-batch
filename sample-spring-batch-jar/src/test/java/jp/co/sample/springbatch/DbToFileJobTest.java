package jp.co.sample.springbatch;

import static org.assertj.core.api.Assertions.*;
import jp.co.sample.common.util.DateFormat.DateFormatVo;
import jp.co.sample.springbatch.config.job.DbToFileJobConfig;
import jp.co.sample.springbatch.framework.util.SystemDateUtils;
import jp.co.sample.springbatch.test.context.SampleSpringBatchTest;
import jp.co.sample.test.util.FileTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SampleSpringBatchTest
@ContextConfiguration(classes = {DbToFileJobConfig.class})
public class DbToFileJobTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  /** トリガーファイルパス. */
  @Value("${sample.file.db-to-file.trigger-file.path}")
  private String triggerFilePath;

  /** トリガーファイル名. */
  @Value("${sample.file.db-to-file.trigger-file.name}")
  private String triggerFileName;

  @BeforeEach
  public void initialize() {
    // ジョブの実行状態を管理する場合はクリア
    // jobRepositoryTestUtils.removeJobExecutions();
    // trigger削除
    FileTestUtils.deleteFile(triggerFilePath, triggerFileName.replace(DateFormatVo.YYYYMMDD_NO_DELIMITER.getCode(), SystemDateUtils.getNowDateAsString()));
  }

  @DisplayName("DbToFileのPre-IT")
  @Test
  public void testJob() throws Exception {
    // --- setup -----
    // --- execute ---
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    // --- verify ----
    assertThat(jobExecution.getExitStatus()).as("結果コードがCOMPLETEDであること").isEqualTo(ExitStatus.COMPLETED);
  }

}
