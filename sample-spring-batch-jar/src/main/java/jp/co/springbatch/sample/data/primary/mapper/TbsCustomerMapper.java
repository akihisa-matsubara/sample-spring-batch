package jp.co.springbatch.sample.data.primary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.springbatch.sample.data.primary.entity.TbsCustomer;
import jp.co.springbatch.sample.data.primary.entity.TbsCustomerExample;

public interface TbsCustomerMapper {
    long countByExample(TbsCustomerExample example);

    int deleteByExample(TbsCustomerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbsCustomer record);

    int insertSelective(TbsCustomer record);

    List<TbsCustomer> selectByExample(TbsCustomerExample example);

    TbsCustomer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbsCustomer record, @Param("example") TbsCustomerExample example);

    int updateByExample(@Param("record") TbsCustomer record, @Param("example") TbsCustomerExample example);

    int updateByPrimaryKeySelective(TbsCustomer record);

    int updateByPrimaryKey(TbsCustomer record);
}