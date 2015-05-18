package com.goweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goweb.dao.impl.LogDao;
import com.goweb.entity.Log;
import com.goweb.service.ILogService;

/**
 * 
 * 日志服务类
 * 
 * 
 *
 */

@Component
@Transactional
public class LogService implements ILogService {
	@Autowired
	private @Qualifier("LogDao")LogDao logDao;

	@Override
	public Log findOne(String property, Object value) {
		List<Log> list = logDao.find(property, value);
		return (list==null||list.size()==0)?null:list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateSingleLogContent(String id, String logContent) {

		Log log = find(id);

		if (log != null) {
			log.setContent(logContent);
			update(log);
		}
	}

	@Override
	public void save(Log entity) {
		logDao.save(entity);
	}

	@Override
	public Log find(String id) {
		return (Log) logDao.find(id);
	}

	@Override
	public List<Log> find(String property, Object value) {
		return logDao.find(property, value);
	}

	@Override
	public List<Log> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Log entity) {
		logDao.update(entity);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Log entity) {
		// TODO Auto-generated method stub

	}

}
