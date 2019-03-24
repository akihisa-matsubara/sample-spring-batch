package jp.co.springbatch.sample.biz.chunk.processor;

import jp.co.springbatch.framework.code.ExecutionContextVo;
import jp.co.springbatch.framework.constant.BatchConst;
import jp.co.springbatch.framework.util.BeanValidationUtils;
import jp.co.springbatch.framework.util.SystemDateUtils;
import jp.co.springbatch.sample.code.BatchVo;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

/**
 * 郵便番号ItemProcessor.
 */
public class PostCodeItemProcessor implements ItemProcessor<PostCodeFileDto, PostCodeEntity> {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(PostCodeItemProcessor.class);

  /** ステップ実行情報. */
  private StepExecution stepExecution;

  /**
   * ステップ実行前処理.
   *
   * @param stepExecution ステップ実行情報
   */
  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.stepExecution = stepExecution;
  }

  /**
   * ステップ実行後処理.
   * 検証エラーレコードがあればログ出力
   */
  @SuppressWarnings("unchecked")
  @AfterStep
  public void afterStep() {
    ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();


    List<PostCodeFileDto> errorRecordList = (List<PostCodeFileDto>)stepExecutionContext.remove(ExecutionContextVo.ERROR_RECORDS.getCode());
    if (errorRecordList == null) {
      return;
    }

    // サンプルでは出力先をログにしています
    errorRecordList.forEach(errorRecord -> LOGGER.warn("validate error record. {}", errorRecord));
  }

  /**
   * 処理.
   *
   * @param postCodeFileDto {@link PostCodeFileDto} 郵便番号FileDto
   * @return {@link PostCodeEntity} 郵便番号マスタEntity
   * @throws Exception 例外
   */
  @Override
  public PostCodeEntity process(final PostCodeFileDto postCodeFileDto) throws Exception {
    validate(postCodeFileDto);

    return convert(postCodeFileDto);
  }

  /**
   * 検証.
   *
   * @param postCodeFileDto {@link PostCodeFileDto} 郵便番号FileDto
   * @throws ConstraintViolationException 検証例外が発生した場合
   */
  @SuppressWarnings("unchecked")
  private void validate(final PostCodeFileDto postCodeFileDto) {
    try {
      BeanValidationUtils.validate(postCodeFileDto);

    } catch (ConstraintViolationException cve) {
      ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();

      List<PostCodeFileDto> errorRecordList = (List<PostCodeFileDto>) stepExecutionContext.get(ExecutionContextVo.ERROR_RECORDS.getCode());;
      if (errorRecordList == null) {
        errorRecordList = new ArrayList<>();
        errorRecordList.add(postCodeFileDto);
        stepExecutionContext.put(ExecutionContextVo.ERROR_RECORDS.getCode(), errorRecordList);

      } else {
        errorRecordList.add(postCodeFileDto);

      }

      throw cve;

    }
  }

  /**
   * 郵便番号マスタEntity -> 郵便番号FileDto変換処理.
   * 共通カラムを設定
   *
   * @param postCodeFileDto {@link PostCodeFileDto} 郵便番号FileDto
   * @return {@link PostCodeEntity} 郵便番号マスタEntity
   */
  private PostCodeEntity convert(final PostCodeFileDto postCodeFileDto) {
    return PostCodeEntity.builder()
        .postCode(postCodeFileDto.getPostCode())
        .prefectureName(postCodeFileDto.getPrefectureNameKanji())
        .cityName(postCodeFileDto.getCityNameKanji())
        .townName(postCodeFileDto.getTownNameKanji())
        // common column
        .version(BatchConst.VERSION_INITIAL_VALUE)
        .creationUserId(BatchVo.FILE_TO_DB.getCode())
        .creationDate(SystemDateUtils.getNowLocalDateTime())
        .updatedUserId(BatchVo.FILE_TO_DB.getCode())
        .updatedDate(SystemDateUtils.getNowLocalDateTime())
        .build();
  }

}
