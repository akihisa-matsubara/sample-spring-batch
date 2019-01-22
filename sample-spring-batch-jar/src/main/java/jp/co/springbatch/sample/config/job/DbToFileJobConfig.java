package jp.co.springbatch.sample.config.job;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import jp.co.springbatch.sample.biz.chunk.processor.CustomerFamilyItemProcessor;
import jp.co.springbatch.sample.biz.chunk.writer.WriteFooterFlatFileCallback;
import jp.co.springbatch.sample.biz.chunk.writer.WriteHeaderFlatFileCallback;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.common.code.FileOperationVo;
import jp.co.springbatch.sample.common.constant.EncodingConst;
import jp.co.springbatch.sample.common.constant.ScopeConst;
import jp.co.springbatch.sample.common.handler.SampleExceptionHandler;
import jp.co.springbatch.sample.common.listener.SampleJobExecutionListener;
import jp.co.springbatch.sample.common.listener.SampleStepExecutionListener;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;
import jp.co.springbatch.sample.data.primary.repository.CustomerFamilyRepository;

/**
 * DB to Fileジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
public class DbToFileJobConfig {

  /** JobBuilderFactory. */
  @Autowired
  private JobBuilderFactory jobs;

  /** StepBuilderFactory. */
  @Autowired
  private StepBuilderFactory steps;

  /** ヘッダーWriterCallback. */
  @Autowired
  private WriteHeaderFlatFileCallback writeHeaderFlatFileCallback;

  /** フッターWriterCallback. */
  @Autowired
  private WriteFooterFlatFileCallback writeFooterFlatFileCallback;

  /** データファイルパス. */
  @Value("${sample.file.db-to-file.data-file.path}")
  private String dataFilePath;

  /** データファイル名. */
  @Value("${sample.file.db-to-file.data-file.name}")
  private String dataFileName;

  /** トリガーファイルパス. */
  @Value("${sample.file.db-to-file.trigger-file.path}")
  private String triggerFilePath;

  /** トリガーファイル名. */
  @Value("${sample.file.db-to-file.trigger-file.name}")
  private String triggerFileName;

  /**********************************************
   * job configurations.
   **********************************************/
  /**
   * DB to Fileジョブ.
   *
   * @param jobExecutionListener ジョブ実行リスナー
   * @param dbToFileCheckTriggerFileStep トリガーファイルチェックステップ
   * @param dbToFileStep DB to Fileステップ
   * @param dbToFileCreateTriggerFileStep トリガーファイル作成ステップ
   * @return Job DB to Fileジョブ
   * @throws Exception 例外
   */
  @Bean
  public Job dbToFileJob(SampleJobExecutionListener jobExecutionListener,
      Step dbToFileCheckTriggerFileStep,
      Step dbToFileStep,
      Step dbToFileCreateTriggerFileStep) throws Exception {
    return jobs.get("dbToFileJob")
        .incrementer(new RunIdIncrementer())
        .listener(jobExecutionListener)
        .start(dbToFileCheckTriggerFileStep)
        .next(dbToFileStep)
        .on(ExitStatus.COMPLETED.getExitCode())
        .to(dbToFileCreateTriggerFileStep)
        .end()
        .build();
  }

  /**********************************************
   * step configurations.
   **********************************************/
  /**
   * トリガーファイルチェックステップ.
   *
   * @return Step トリガーファイルチェックステップ
   */
  @Bean
  public Step dbToFileCheckTriggerFileStep() {
    return steps.get("dbToFileCheckTriggerFileStep")
        .tasklet(dbToFileCheckTriggerFileTasklet())
        .build();
  }

  /**
   * DB to Fileステップ.
   *
   * @param primarySqlSessionFactory 主DB用SqlSessionFactory
   * @param stepExecutionListener ステップ実行リスナー
   * @param sampleExceptionHandler 例外ハンドラー
   * @return Step DB to Fileステップ.
   */
  @Bean
  public Step dbToFileStep(SqlSessionFactory primarySqlSessionFactory,
      SampleStepExecutionListener stepExecutionListener,
      SampleExceptionHandler sampleExceptionHandler) {
    return steps.get("dbToFileStep")
        .<CustomerFamilyEntity, CustomerFamilyFileDto>chunk(10)
        .reader(dbToFileItemReader(primarySqlSessionFactory))
        .processor(dbToFileItemProcessor())
        .writer(dbToFileItemWriter())
        .faultTolerant()
        .listener(stepExecutionListener)
        .exceptionHandler(sampleExceptionHandler)
        .build();
  }

  /**
   * トリガーファイル作成ステップ.
   *
   * @return Step トリガーファイル作成ステップ
   */
  @Bean
  public Step dbToFileCreateTriggerFileStep() {
    return steps.get("dbToFileCreateTriggerFileStep")
        .tasklet(dbToFileCreateTriggerFileTasklet())
        .build();
  }

  /**
   * トリガーファイルチェック処理.
   *
   * @return TriggerFileTasklet トリガーファイル処理
   */
  @Bean
  public TriggerFileTasklet dbToFileCheckTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(FileOperationVo.CHECK_CREATE);
    tasklet.setFilePath(triggerFilePath);
    tasklet.setFileName(triggerFileName);
    return tasklet;
  }

  /**
   * トリガーファイル作成処理.
   *
   * @return TriggerFileTasklet トリガーファイル作成処理
   */
  @Bean
  public TriggerFileTasklet dbToFileCreateTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(FileOperationVo.CREATE);
    tasklet.setFilePath(triggerFilePath);
    tasklet.setFileName(triggerFileName);
    return tasklet;
  }

  /**********************************************
   * reader processor writer configurations.
   **********************************************/
  /**
   * DB to File ItemReader.
   *
   * @param primarySqlSessionFactory 主DB用SqlSessionFactory
   * @return MyBatisCursorItemReader DB to File ItemReader
   */
  @Bean
  public MyBatisCursorItemReader<CustomerFamilyEntity> dbToFileItemReader(SqlSessionFactory primarySqlSessionFactory) {
    MyBatisCursorItemReader<CustomerFamilyEntity> reader = new MyBatisCursorItemReader<>();
    reader.setSqlSessionFactory(primarySqlSessionFactory);
    reader.setQueryId(CustomerFamilyRepository.SELECT_ALL);
    return reader;
  }

  /**
   * DB to File ItemProcessor.
   *
   * @return CustomerFamilyItemProcessor 顧客家族ItemProcessor
   */
  @Bean
  public CustomerFamilyItemProcessor dbToFileItemProcessor() {
    return new CustomerFamilyItemProcessor();
  }

  /**
   * DB to File ItemWriter.
   *
   * @return FlatFileItemWriter DB to File ItemWriter
   */
  @Bean
  public FlatFileItemWriter<CustomerFamilyFileDto> dbToFileItemWriter() {
    return new FlatFileItemWriterBuilder<CustomerFamilyFileDto>()
        .name("dbToFileItemWriter")
        .resource(new FileSystemResource(dataFilePath + "/" + dataFileName))
        .delimited()
        .delimiter(",")
        .names(CustomerFamilyFileDto.FIELD)
        .encoding(EncodingConst.MS932)
        .headerCallback(writeHeaderFlatFileCallback)
        .footerCallback(writeFooterFlatFileCallback)
        .build();
  }
}
