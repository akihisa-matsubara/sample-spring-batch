package jp.co.springbatch.sample.biz.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.springbatch.sample.common.util.SampleDateUtils;
import jp.co.springbatch.sample.data.dto.PostCodeFileDto;
import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

public class PostCodeItemProcessor implements ItemProcessor<PostCodeFileDto, PostCodeEntity> {

	@Autowired
	private SampleDateUtils dateUtils;

	@Override
	public PostCodeEntity process(final PostCodeFileDto postCodeFileDto) throws Exception {
		return convert(postCodeFileDto);
	}

	private PostCodeEntity convert(final PostCodeFileDto postCodeFileDto) {
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
