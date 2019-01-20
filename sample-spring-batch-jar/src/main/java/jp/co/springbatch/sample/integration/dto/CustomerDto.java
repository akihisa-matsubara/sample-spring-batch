package jp.co.springbatch.sample.integration.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

	private String customerNo;

	private String nameKanji;

	private String nameKana;

	private String gender;

//	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private String addressZip;

	private String address;

}
