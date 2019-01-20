package jp.co.springbatch.sample.data.primary.repository;

import org.apache.ibatis.annotations.Mapper;

import jp.co.springbatch.sample.data.primary.entity.PostCodeEntity;

@Mapper
public interface PostCodeRepository {

    int insert(PostCodeEntity record);

}