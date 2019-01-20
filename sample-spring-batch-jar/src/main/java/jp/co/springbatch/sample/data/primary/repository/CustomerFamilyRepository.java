package jp.co.springbatch.sample.data.primary.repository;

import java.util.List;

import jp.co.springbatch.sample.data.primary.entity.CustomerFamilyEntity;

public interface CustomerFamilyRepository {

	List<CustomerFamilyEntity> selectAll();

}