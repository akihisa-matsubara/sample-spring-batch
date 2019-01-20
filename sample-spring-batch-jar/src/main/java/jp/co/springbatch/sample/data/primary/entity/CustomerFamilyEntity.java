package jp.co.springbatch.sample.data.primary.entity;

import java.util.Date;

import lombok.Data;

// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Data
public class CustomerFamilyEntity {

	private String customerNo;

	private String customerNameKanji;

	private String customerNameKana;

	private String customerGender;

	private Date customerBirthday;

	private String familyNo;

	private String familyNameKanji;

	private String familyNameKana;

	private String familyGender;

	private Date familyBirthday;
}