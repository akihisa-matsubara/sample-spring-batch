package jp.co.springbatch.sample.data.primary.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

@Mapper
public interface CustomerFamilyRepository {

	List<CustomerFamilyEntity> selectAll();

}