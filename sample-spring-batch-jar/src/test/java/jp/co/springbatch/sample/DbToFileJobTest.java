package jp.co.springbatch.sample;

import jp.co.springbatch.sample.config.job.DbToFileJobConfig;
import jp.co.springbatch.test.context.SampleSpringBatchTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SampleSpringBatchTest
@ContextConfiguration(classes = {DbToFileJobConfig.class})
public class DbToFileJobTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  // @Autowired
  // private JobRepositoryTestUtils jobRepositoryTestUtils;
  //
  // @Autowired
  // private JdbcTemplate jdbcTemplate;

  @Test
  public void testJob() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
  }

}
