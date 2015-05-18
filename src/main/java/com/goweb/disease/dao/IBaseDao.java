package com.goweb.disease.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * Dao层基类接口
 * 
 * @author yinsheng
 *
 * @param <T>
 * @param <ID>
 * 
 */
public interface IBaseDao<T,ID extends Serializable> {

	public void save(T entity);

	public T find(ID id);
	
	public List<T> find(String property,Object value);
	
	//public T findOne(String property, Object value);
	
	//public List<T> findAll();
	
	//public long count();

	public void update(T entity);
	
	//public void delete(ID id);

	//public void delete(T entity);
	
	//public void delete(List<T> list);
	
}
