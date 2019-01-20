package jp.co.springbatch.sample.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerFamilyFileDto {
	public static final String[] FIELD = new String[] {
			"customerNo",
			"customerNameKanji",
			"customerNameKana",
			"customerGender",
			"customerBirthday",
			"familyNo",
			"familyNameKanji",
			"familyNameKana",
			"familyGender",
			"familyBirthday" };

	private String customerNo;

	private String customerNameKanji;

	private String customerNameKana;

	private String customerGender;

	private String customerBirthday;

	private String familyNo;

	private String familyNameKanji;

	private String familyNameKana;

	private String familyGender;

	private String familyBirthday;

}
