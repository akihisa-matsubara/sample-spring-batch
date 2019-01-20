package jp.co.springbatch.sample.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCodeFileDto {
	public static final String[] FIELD = new String[] {
			"postCode",
			"prefectureNameKana",
			"cityNameKana",
			"townNameKana",
			"prefectureNameKanji",
			"cityNameKanji",
			"townNameKanji" };

	private String postCode;

	private String prefectureNameKana;

	private String cityNameKana;

	private String townNameKana;

	private String prefectureNameKanji;

	private String cityNameKanji;

	private String townNameKanji;

}
