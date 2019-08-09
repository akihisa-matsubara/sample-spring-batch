package jp.co.sample.springbatch.biz.tasklet;

import jp.co.sample.common.code.DateFormat.DateFormatVo;
import jp.co.sample.springbatch.framework.code.TriggerFileOperationVo;
import jp.co.sample.springbatch.framework.util.SystemDateUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * トリガーファイル処理.
 */
public class TriggerFileTasklet implements Tasklet, InitializingBean {

  /** トリガーファイル操作VO. */
  @Setter
  private TriggerFileOperationVo operation;

  /** ファイルパス. */
  @Setter
  private String filePath;

  /** ファイル名. */
  @Setter
  private String fileName;

  /**
   * プロパティ設定後処理.
   *
   * @throws IllegalArgumentException プロパティがnull値の場合
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
   * @param contribution {@link StepContribution}
   * @param chunkContext {@link ChunkContext}
   * @return {@link RepeatStatus} 結果ステータス
   * @throws IOException ファイルへの書き込みまたはファイルの作成／削除中に入出力エラーが発生した場合
   * @throws IllegalStateException トリガーファイルの状態が不正な場合
   * @throws Exception 例外
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    String replaceFileName = fileName.replace(DateFormatVo.YYYYMMDD_NO_DELIMITER.getCode(), SystemDateUtils.getNowDateAsString());

    validate(replaceFileName);

    process(replaceFileName);

    return RepeatStatus.FINISHED;
  }

  /**
   * 検証.
   * 対象操作に従い、トリガーファイルの状態を検証します
   *
   * @param targetFile 対象ファイル
   * @throws IllegalStateException トリガーファイルの状態が不正な場合
   */
  private void validate(String targetFile) {
    Assert.isTrue(Paths.get(filePath).toFile().isDirectory(), "not a directory.");

    switch (operation) {
      case CREATE_CHECK:
        Assert.state(!Paths.get(filePath, targetFile).toFile().exists(), "trigger file exists. trigger file=" + filePath + "/" + targetFile);
        break;

      case DELETE_CHECK:
        Assert.state(Paths.get(filePath, targetFile).toFile().exists(), "trigger file does not exists. trigger file=" + filePath + "/" + targetFile);
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
   * @throws IOException ファイルへの書き込みまたはファイルの作成／削除中に入出力エラーが発生した場合
   */
  private void process(String targetFile) throws IOException {
    switch (operation) {
      case CREATE_PROCCESS:
        Files.write(Paths.get(filePath, targetFile), SystemDateUtils.getNowDateAsString().getBytes());
        break;

      case DELETE_PROCCESS:
        Files.delete(Paths.get(filePath, targetFile));
        break;

      default:
        break;
    }
  }
}
