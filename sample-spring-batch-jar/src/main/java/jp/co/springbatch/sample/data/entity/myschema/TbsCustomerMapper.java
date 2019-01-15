package jp.co.springbatch.sample.data.entity.myschema;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbsCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbsCustomer record);

    int insertSelective(TbsCustomer record);

    TbsCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbsCustomer record);

    int updateByPrimaryKey(TbsCustomer record);
}