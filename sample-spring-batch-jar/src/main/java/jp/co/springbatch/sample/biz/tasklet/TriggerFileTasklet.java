package jp.co.springbatch.sample.biz.tasklet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import jp.co.springbatch.sample.common.code.FileOperationVo;
import jp.co.springbatch.sample.common.util.SampleDateUtils;
import lombok.Setter;

/**
 * トリガーファイル処理.
 */
//sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@StepScope
@Component
public class TriggerFileTasklet implements Tasklet, InitializingBean {

  /** ファイル操作VO. */
  @Setter
  private FileOperationVo operation;

  /** ファイルパス. */
  @Setter
  private String filePath;

  /** ファイル名. */
  @Setter
  private String fileName;

  /** 日付ユーティリティー. */
  @Autowired
  private SampleDateUtils dateUtils;

  /**
   * プロパティ設定後処理.
   *
   * @throws Exception 例外
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(operation, "operation must be set.");
    Assert.notNull(filePath, "trigger file path must be set.");
    Assert.notNull(fileName, "trigger file name must be set.");
  }

  /**
   * 実行.
   *
   * @param contribution StepContribution
   * @param chunkContext ChunkContext
   * @return RepeatStatus 結果ステータス
   * @throws Exception 例外
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    String systemDate = dateUtils.getNowDateString();
    String replaceFileName = fileName.replace("YYYYMMDD", systemDate);

    validate(replaceFileName);

    process(replaceFileName, systemDate);

    return RepeatStatus.FINISHED;
  }

  /**
   * 検証.
   * 対象操作に従い、トリガーファイルの状態を検証します
   *
   * @param targetFile 対象ファイル
   */
  private void validate(String targetFile) {
    Path path = Paths.get(filePath);
    Assert.isTrue(Files.isDirectory(path), "not a directory.");

    switch (operation) {
      case CHECK_CREATE:
        Assert.isTrue(!Files.exists(Paths.get(filePath, targetFile)), "trigger file exists. trigger file=" + filePath + "/" + targetFile);
        break;

      case CHECK_DELETE:
        Assert.isTrue(Files.exists(Paths.get(filePath, targetFile)),
            "trigger file does not exists. trigger file=" + filePath + "/" + targetFile);
        break;

      default:
        break;
    }
  }

  /**
   * 処理.
   * 対象操作に従い、トリガーファイルの作成／削除を実施します
   *
   * @param targetFile 対象ファイル
   * @param systemDate システム日付
   * @throws IOException ファイルへの書き込みまたはファイルの作成／削除中に入出力エラーが発生した場合
   */
  private void process(String targetFile, String systemDate) throws IOException {
    switch (operation) {
      case CREATE:
        Files.write(Paths.get(filePath, targetFile), systemDate.getBytes());
        break;

      case DELETE:
        Files.delete(Paths.get(filePath, targetFile));
        break;

      default:
        break;
    }
  }
}
