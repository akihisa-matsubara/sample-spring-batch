package jp.co.springbatch.sample.config.job;

import jp.co.springbatch.framework.code.EncodingVo;
import jp.co.springbatch.framework.code.TriggerFileOperationVo;
import jp.co.springbatch.framework.constant.ScopeConst;
import jp.co.springbatch.framework.handler.SampleExceptionHandler;
import jp.co.springbatch.framework.listener.SampleJobExecutionListener;
import jp.co.springbatch.framework.listener.SampleStepExecutionListener;
import jp.co.springbatch.sample.biz.chunk.processor.CustomerFamilyItemProcessor;
import jp.co.springbatch.sample.biz.chunk.writer.WriteFooterFlatFileCallback;
import jp.co.springbatch.sample.biz.chunk.writer.WriteHeaderFlatFileCallback;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.data.dto.CustomerFamilyFileDto;
import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;
import jp.co.springbatch.sample.data.primary.repository.CustomerFamilyRepository;
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

/**
 * DB to Fileジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
public class DbToFileJobConfig {

  /** StepBuilderFactory. */
  @Autowired
  private StepBuilderFactory steps;

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
   * @param jobs {@link JobBuilderFactory}
   * @param jobExecutionListener {@link SampleJobExecutionListener} ジョブ実行リスナー
   * @param dbToFileCheckTriggerFileStep トリガーファイルチェックステップ
   * @param dbToFileStep DB to Fileステップ
   * @param dbToFileCreateTriggerFileStep トリガーファイル作成ステップ
   * @return {@link Job} DB to Fileジョブ
   */
  @Bean
  public Job dbToFileJob(JobBuilderFactory jobs,
      SampleJobExecutionListener jobExecutionListener,
      Step dbToFileCheckTriggerFileStep,
      Step dbToFileStep,
      Step dbToFileCreateTriggerFileStep) {
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
   * @return {@link Step} トリガーファイルチェックステップ
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
   * @param primarySqlSessionFactory {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @param writeHeaderFlatFileCallback {@link WriteHeaderFlatFileCallback} ヘッダーWriterCallback
   * @param writeFooterFlatFileCallback {@link WriteFooterFlatFileCallback} フッターWriterCallback
   * @param stepExecutionListener {@link SampleStepExecutionListener} ステップ実行リスナー
   * @param sampleExceptionHandler {@link SampleExceptionHandler} 例外ハンドラー
   * @return {@link Step} DB to Fileステップ.
   */
  @Bean
  public Step dbToFileStep(SqlSessionFactory primarySqlSessionFactory,
      WriteHeaderFlatFileCallback writeHeaderFlatFileCallback,
      WriteFooterFlatFileCallback writeFooterFlatFileCallback,
      SampleStepExecutionListener stepExecutionListener,
      SampleExceptionHandler sampleExceptionHandler) {
    return steps.get("dbToFileStep")
        .<CustomerFamilyEntity, CustomerFamilyFileDto>chunk(10)
        .reader(dbToFileItemReader(primarySqlSessionFactory))
        .processor(dbToFileItemProcessor())
        .writer(dbToFileItemWriter(writeHeaderFlatFileCallback, writeFooterFlatFileCallback))
        .faultTolerant()
        .listener(stepExecutionListener)
        .exceptionHandler(sampleExceptionHandler)
        .build();
  }

  /**
   * トリガーファイル作成ステップ.
   *
   * @return {@link Step} トリガーファイル作成ステップ
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
   * @return {@link TriggerFileTasklet} トリガーファイル処理
   */
  @Bean
  public TriggerFileTasklet dbToFileCheckTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(TriggerFileOperationVo.CREATE_CHECK);
    tasklet.setFilePath(triggerFilePath);
    tasklet.setFileName(triggerFileName);
    return tasklet;
  }

  /**
   * トリガーファイル作成処理.
   *
   * @return {@link TriggerFileTasklet} トリガーファイル作成処理
   */
  @Bean
  public TriggerFileTasklet dbToFileCreateTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(TriggerFileOperationVo.CREATE_PROCCESS);
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
   * @param primarySqlSessionFactory {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @return {@link MyBatisCursorItemReader} DB to File ItemReader
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
   * @return {@link CustomerFamilyItemProcessor} 顧客家族ItemProcessor
   */
  @Bean
  public CustomerFamilyItemProcessor dbToFileItemProcessor() {
    return new CustomerFamilyItemProcessor();
  }

  /**
   * DB to File ItemWriter.
   *
   * @param writeHeaderFlatFileCallback {@link WriteHeaderFlatFileCallback} ヘッダーWriterCallback
   * @param writeFooterFlatFileCallback {@link WriteFooterFlatFileCallback} フッターWriterCallback
   * @return {@link FlatFileItemWriter} DB to File ItemWriter
   */
  @Bean
  public FlatFileItemWriter<CustomerFamilyFileDto> dbToFileItemWriter(WriteHeaderFlatFileCallback writeHeaderFlatFileCallback,
      WriteFooterFlatFileCallback writeFooterFlatFileCallback) {
    return new FlatFileItemWriterBuilder<CustomerFamilyFileDto>()
        .name("dbToFileItemWriter")
        .resource(new FileSystemResource(dataFilePath + "/" + dataFileName))
        .delimited()
        .delimiter(",")
        .names(CustomerFamilyFileDto.getFields())
        .encoding(EncodingVo.MS932.getCode())
        .headerCallback(writeHeaderFlatFileCallback)
        .footerCallback(writeFooterFlatFileCallback)
        .build();
  }
}
