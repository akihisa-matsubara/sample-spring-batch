package jp.co.springbatch.sample.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerFileDto {

    private String name;
    private String address;
    private String tel;

}
