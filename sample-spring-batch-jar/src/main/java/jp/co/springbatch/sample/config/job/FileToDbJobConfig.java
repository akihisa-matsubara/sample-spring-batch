package jp.co.springbatch.sample.config.job;

import jp.co.springbatch.framework.code.EncodingVo;
import jp.co.springbatch.framework.code.TriggerFileOperationVo;
import jp.co.springbatch.framework.constant.ScopeConst;
import jp.co.springbatch.framework.handler.SampleExceptionHandler;
import jp.co.springbatch.framework.item.mapper.FieldSetMapper;
import jp.co.springbatch.framework.listener.SampleJobExecutionListener;
import jp.co.springbatch.framework.listener.SampleStepExecutionListener;
import jp.co.springbatch.sample.biz.chunk.processor.PostCodeItemProcessor;
import jp.co.springbatch.sample.biz.chunk.reader.ReadSkippedLinesCallback;
import jp.co.springbatch.sample.biz.tasklet.TriggerFileTasklet;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;
import jp.co.springbatch.sample.data.query.QueryId;
import java.io.IOException;
import javax.validation.ConstraintViolationException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * File to DBジョブ設定.
 */
@Scope(ScopeConst.SINGLETON)
@Configuration
@EnableBatchProcessing
public class FileToDbJobConfig {

  /** StepBuilderFactory. */
  @Autowired
  private StepBuilderFactory steps;

  /** データファイルパス. */
  @Value("${sample.file.file-to-db.data-file.path}")
  private String dataFilePath;

  /** データファイル名. */
  @Value("${sample.file.file-to-db.data-file.name}")
  private String dataFileName;

  /** トリガーファイルパス. */
  @Value("${sample.file.file-to-db.trigger-file.path}")
  private String triggerFilePath;

  /** トリガーファイル名. */
  @Value("${sample.file.file-to-db.trigger-file.name}")
  private String triggerFileName;

  /**********************************************
   * job configurations.
   **********************************************/
  /**
   * File to DBジョブ.
   *
   * @param jobs {@link JobBuilderFactory} JobBuilderFactory
   * @param jobExecutionListener {@link SampleJobExecutionListener} ジョブ実行リスナー
   * @param fileToDbCheckTriggerFileStep トリガーファイルチェックステップ
   * @param fileToDbStep File to DBステップ
   * @param fileToDbDeleteTriggerFileStep トリガーファイル削除ステップ
   * @return {@link Job} File to DBジョブ
   * @throws Exception 例外
   */
  @Bean
  public Job fileToDbJob(JobBuilderFactory jobs,
      SampleJobExecutionListener jobExecutionListener,
      Step fileToDbCheckTriggerFileStep,
      Step fileToDbStep,
      Step fileToDbDeleteTriggerFileStep) {
    return jobs.get("fileToDbJob")
        .incrementer(new RunIdIncrementer())
        .listener(jobExecutionListener)
        .start(fileToDbCheckTriggerFileStep)
        .next(fileToDbStep)
        .on(ExitStatus.COMPLETED.getExitCode())
        .to(fileToDbDeleteTriggerFileStep)
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
  public Step fileToDbCheckTriggerFileStep() {
    return steps.get("fileToDbCheckTriggerFileStep")
        .tasklet(fileToDbCheckTriggerFileTasklet())
        .build();
  }

  /**
   * File to DBステップ.
   *
   * @param readSkippedLinesCallback {@link ReadSkippedLinesCallback} ReadSkippedLinesCallback
   * @param primarySqlSessionFactory {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @param stepExecutionListener {@link SampleStepExecutionListener} ステップ実行リスナー
   * @param sampleExceptionHandler {@link SampleExceptionHandler} 例外ハンドラー
   * @param primaryTxManager {@link PlatformTransactionManager} 主DB用TransactionManager
   * @return {@link Step} File to DBステップ.
   */
  @Bean
  public Step fileToDbStep(ReadSkippedLinesCallback readSkippedLinesCallback,
      SqlSessionFactory primarySqlSessionFactory,
      SampleStepExecutionListener stepExecutionListener,
      SampleExceptionHandler sampleExceptionHandler,
      PlatformTransactionManager primaryTxManager) {
    return steps.get("fileToDbStep")
        .<PostCodeFileDto, PostCodeEntity>chunk(10)
        .reader(fileToDbItemReader(readSkippedLinesCallback))
        .processor(fileToDbItemProcessor())
        .writer(fileToDbItemWriter(primarySqlSessionFactory))
        .faultTolerant()
        .skip(FlatFileParseException.class)
        .skip(ConstraintViolationException.class)
        .skip(EmptyResultDataAccessException.class) // Insertが空振りした場合、SkipするがRollbackはする
        .noSkip(IOException.class)
        .noRollback(FlatFileParseException.class)
        .noRollback(ConstraintViolationException.class)
        .skipLimit(Integer.MAX_VALUE) // Unlimited
        .listener(stepExecutionListener)
        .exceptionHandler(sampleExceptionHandler)
        .transactionManager(primaryTxManager)
        .build();
  }

  /**
   * トリガーファイル削除ステップ.
   *
   * @return {@link Step} トリガーファイル削除ステップ
   */
  @Bean
  public Step fileToDbDeleteTriggerFileStep() {
    return steps.get("fileToDbDeleteTriggerFileStep")
        .tasklet(fileToDbDeleteTriggerFileTasklet())
        .build();
  }

  /**
   * トリガーファイルチェック処理.
   *
   * @return {@link TriggerFileTasklet} トリガーファイル処理
   */
  @Bean
  public TriggerFileTasklet fileToDbCheckTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(TriggerFileOperationVo.DELETE_CHECK);
    tasklet.setFilePath(triggerFilePath);
    tasklet.setFileName(triggerFileName);
    return tasklet;
  }

  /**
   * トリガーファイル削除処理.
   *
   * @return {@link TriggerFileTasklet} トリガーファイル削除処理
   */
  @Bean
  public TriggerFileTasklet fileToDbDeleteTriggerFileTasklet() {
    TriggerFileTasklet tasklet = new TriggerFileTasklet();
    tasklet.setOperation(TriggerFileOperationVo.DELETE_PROCCESS);
    tasklet.setFilePath(triggerFilePath);
    tasklet.setFileName(triggerFileName);
    return tasklet;
  }

  /**********************************************
   * reader processor writer configurations.
   **********************************************/
  /**
   * File to DB ItemReader.
   *
   * @param readSkippedLinesCallback {@link ReadSkippedLinesCallback}
   * @return {@link FlatFileItemReader} File to DB ItemReader
   */
  @Bean
  public FlatFileItemReader<PostCodeFileDto> fileToDbItemReader(ReadSkippedLinesCallback readSkippedLinesCallback) {
    return new FlatFileItemReaderBuilder<PostCodeFileDto>().name("fileToDbItemReader")
        .resource(new FileSystemResource(dataFilePath + "/" + dataFileName))
        .linesToSkip(1) // header line skip
        .skippedLinesCallback(readSkippedLinesCallback)
        .delimited()
        .delimiter(",")
        .names(PostCodeFileDto.getFields())
        .encoding(EncodingVo.MS932.getCode())
        .fieldSetMapper(new FieldSetMapper<PostCodeFileDto>(PostCodeFileDto.class)).build();
  }

  /**
   * File to DB ItemProcessor.
   *
   * @return {@link PostCodeItemProcessor} 郵便番号ItemProcessor
   */
  @Bean
  public PostCodeItemProcessor fileToDbItemProcessor() {
    return new PostCodeItemProcessor();
  }

  /**
   * File to DB ItemWriter.
   *
   * @param primarySqlSessionFactory {@link SqlSessionFactory} 主DB用SqlSessionFactory
   * @return {@link MyBatisBatchItemWriter} File to DB ItemWriter
   */
  @Bean
  public MyBatisBatchItemWriter<PostCodeEntity> fileToDbItemWriter(SqlSessionFactory primarySqlSessionFactory) {
    MyBatisBatchItemWriter<PostCodeEntity> writer = new MyBatisBatchItemWriter<>();
    writer.setSqlSessionFactory(primarySqlSessionFactory);
    writer.setStatementId(QueryId.POST_CODE_INSERT.getId());
    return writer;
  }

}
