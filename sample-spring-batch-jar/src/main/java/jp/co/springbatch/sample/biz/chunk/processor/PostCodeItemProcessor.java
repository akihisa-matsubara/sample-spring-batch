package jp.co.springbatch.sample.biz.chunk.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

/**
 * 郵便番号ItemProcessor.
 */
public class PostCodeItemProcessor implements ItemProcessor<PostCodeFileDto, PostCodeEntity> {

  /** 日付ユーティリティー. */
  @Autowired
  private SampleDateUtils dateUtils;

  /**
   * 処理.
   *
   * @param postCodeFileDto 郵便番号FileDto
   * @return PostCodeEntity 郵便番号マスタEntity
   * @throws Exception 例外
   */
  @Override
  public PostCodeEntity process(final PostCodeFileDto postCodeFileDto) throws Exception {
    return convert(postCodeFileDto);
  }

  /**
   * 郵便番号マスタEntity -> 郵便番号FileDto変換処理.
   * 共通カラムを設定
   *
   * @param postCodeFileDto 郵便番号FileDto
   * @return PostCodeEntity 郵便番号マスタEntity
   */
  private PostCodeEntity convert(final PostCodeFileDto postCodeFileDto) {
    // validate
//    SpringValidator<PostCodeFileDto> validator = new SpringValidator<>();
//    validator.setValidator(new LocalValidatorFactoryBean());
//    validator.validate(postCodeFileDto);

    PostCodeEntity entity = new PostCodeEntity();
    entity.setPostCode(postCodeFileDto.getPostCode());
    entity.setPrefectureName(postCodeFileDto.getPrefectureNameKanji());
    entity.setCityName(postCodeFileDto.getCityNameKanji());
    entity.setTownName(postCodeFileDto.getTownNameKanji());

    // common culomn
    entity.setVersion(1);
    entity.setCreationUserId("fileToDb");
    entity.setCreationDate(dateUtils.getNowLocalDateTime());
    entity.setUpdatedUserId("fileToDb");
    entity.setUpdatedDate(dateUtils.getNowLocalDateTime());

    return entity;
  }

}
