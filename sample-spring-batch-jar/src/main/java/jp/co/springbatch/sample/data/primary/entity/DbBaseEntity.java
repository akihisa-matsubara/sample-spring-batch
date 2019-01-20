package jp.co.springbatch.sample.data.primary.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DbBaseEntity {

	private int version;

	private String creationUserId;

	private LocalDateTime creationDate;

	private String updatedUserId;

	private LocalDateTime updatedDate;

}
