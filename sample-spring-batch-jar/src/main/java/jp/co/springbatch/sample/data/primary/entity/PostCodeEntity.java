package jp.co.springbatch.sample.data.primary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Data
@EqualsAndHashCode(callSuper = true)
public class PostCodeEntity extends DbBaseEntity {

	private String postCode;

	private String prefectureName;

	private String cityName;

	private String townName;

}
