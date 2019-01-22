package jp.co.springbatch.sample.biz.chunk.processor;

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
import jp.co.springbatch.sample.common.constant.ExecutionContextConst;
import jp.co.springbatch.sample.common.util.SampleBeanValidationUtils;
import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

/**
 * 郵便番号ItemProcessor.
 */
public class PostCodeItemProcessor implements ItemProcessor<PostCodeFileDto, PostCodeEntity> {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(PostCodeItemProcessor.class);

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

    if (!stepExecutionContext.containsKey(ExecutionContextConst.ERROR_RECORD_LIST)) {
      return;
    }

    List<PostCodeFileDto> errorRecordList = (List<PostCodeFileDto>)stepExecutionContext.remove(ExecutionContextConst.ERROR_RECORD_LIST);

    // サンプルでは出力先をログにしています
    for (PostCodeFileDto dto : errorRecordList) {
      log.warn("validate error record. {}", dto);

    }
  }

  /**
   * 処理.
   *
   * @param postCodeFileDto 郵便番号FileDto
   * @return PostCodeEntity 郵便番号マスタEntity
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
   * @param postCodeFileDto 郵便番号FileDto
   * @throws ConstraintViolationException 検証例外が発生した場合
   */
  @SuppressWarnings("unchecked")
  private void validate(final PostCodeFileDto postCodeFileDto) {
    try {
      SampleBeanValidationUtils.validate(postCodeFileDto);

    } catch (ConstraintViolationException cv) {
      ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();

      List<PostCodeFileDto> errorRecordList = null;
      if (!stepExecutionContext.containsKey(ExecutionContextConst.ERROR_RECORD_LIST)) {
        errorRecordList = new ArrayList<>();
        stepExecutionContext.put(ExecutionContextConst.ERROR_RECORD_LIST, errorRecordList);

      } else {
        errorRecordList = (List<PostCodeFileDto>) stepExecutionContext.get(ExecutionContextConst.ERROR_RECORD_LIST);

      }

      errorRecordList.add(postCodeFileDto);
      throw cv;

    }
  }

  /**
   * 郵便番号マスタEntity -> 郵便番号FileDto変換処理.
   * 共通カラムを設定
   *
   * @param postCodeFileDto 郵便番号FileDto
   * @return PostCodeEntity 郵便番号マスタEntity
   */
  private PostCodeEntity convert(final PostCodeFileDto postCodeFileDto) {
    PostCodeEntity entity = new PostCodeEntity();
    entity.setPostCode(postCodeFileDto.getPostCode());
    entity.setPrefectureName(postCodeFileDto.getPrefectureNameKanji());
    entity.setCityName(postCodeFileDto.getCityNameKanji());
    entity.setTownName(postCodeFileDto.getTownNameKanji());

    // common culomn
    entity.setVersion(1);
    entity.setCreationUserId("fileToDb");
    entity.setCreationDate(SampleDateUtils.getNowLocalDateTime());
    entity.setUpdatedUserId("fileToDb");
    entity.setUpdatedDate(SampleDateUtils.getNowLocalDateTime());

    return entity;
  }

}
