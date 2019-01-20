package jp.co.springbatch.sample.data.primary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostCodeEntity extends DbBaseEntity {

	private String postCode;

	private String prefectureName;

	private String cityName;

	private String townName;

}
