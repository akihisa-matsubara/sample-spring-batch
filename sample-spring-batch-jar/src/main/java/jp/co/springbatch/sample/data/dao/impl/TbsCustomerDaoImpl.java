//package jp.co.springbatch.sample.data.dao.impl;
//
//import org.mybatis.spring.support.SqlSessionDaoSupport;
//
//import jp.co.springbatch.sample.data.dao.TbsCustomerDao;
//import jp.co.springbatch.sample.data.entity.myschema.TbsCustomer;
//
//public class TbsCustomerDaoImpl extends SqlSessionDaoSupport implements TbsCustomerDao {
//
//	public TbsCustomer findById(Long id) {
//		return (TbsCustomer) getSqlSession().selectOne("jp.co.springbatch.sample.data.entity.myschema.TbsCustomerMapper.selectByPrimaryKey", id);
//	}
//
//}