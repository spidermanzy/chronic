package com.goweb.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goweb.dao.impl.LogDao;
import com.goweb.dao.impl.MessageDao;
import com.goweb.entity.Message;
import com.goweb.model.page.Page;
import com.goweb.model.page.PageParemeter;
import com.goweb.service.IMessageService;
import com.goweb.web.vo.StaticsReportVo;

/**
 * 
 * 日志消息服务类
 * 
 * @author yinsheng
 *
 */

@Component
@Transactional
public class MessageService implements IMessageService {
	
	@Autowired
	private @Qualifier("LogDao")LogDao logDao;

	@Autowired
	private @Qualifier("MessageDao")MessageDao messageDao;
	
	@Override
	public List<Message> getMessageOnDay(Date date) {
		
		return messageDao.getMessageOnDay(date);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveSingleMessage(Message message) {
		
		messageDao.save(message);
		
	}

	@Override
	public List<Message> getMessage(Date beginDate, Date endDate) {
		return messageDao.getMessage(beginDate, endDate);
	}

	@Override
	public List<Message> getMessageByLogDate() {
		return messageDao.getMessageByLogDate();
	}

	@Override
	public List<StaticsReportVo> getStaticOnDay(Date date) {
		return messageDao.getStaticOnDay(date);
	}

	@Override
	public List<StaticsReportVo> getStatic(Date beginDate, Date endDate) {
		return messageDao.getStatic(beginDate, endDate);
	}

	@Override
	public Page<Message> findAll(PageParemeter pageParemeter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Message entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> find(String property, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Message entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Message entity) {
		// TODO Auto-generated method stub
		
	}

	
}
