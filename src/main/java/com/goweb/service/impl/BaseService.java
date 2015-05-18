package com.goweb.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.goweb.dao.IBaseDao;
import com.goweb.service.IBaseService;



/**
 * 
 * Service基类
 * 
 * @author yinsheng
 *	
 */
@Transactional
public class BaseService<T, ID extends Serializable> implements IBaseService <T,ID > {
	
	@Autowired
	private IBaseDao<T,ID>  baseDao;
	
	@Override
	public void save(T entity) {
		baseDao.save(entity);
		
	}

	@Override
	public T find(ID id) {
		return baseDao.find(id);
	}

	@Override
	public List<T> find(String property, Object value) {
		return baseDao.find(property, value);
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(ID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}
	
}
