package com.goweb.disease.service;

import java.util.Date;
import java.util.List;

import com.goweb.disease.entity.Message;
import com.goweb.disease.web.vo.StaticsReportVo;

/**
 * 
 * 日志消息接口类
 * 
 * @author yinsheng
 *
 */

public interface IMessageService extends IPageBaseService<Message, String> {
	
	
	public List<Message> getMessageOnDay(Date date);
	
	public void saveSingleMessage(Message message);
	
	List<Message> getMessage(Date beginDate,Date endDate);
	
	List<Message> getMessageByLogDate();
	
	public List<StaticsReportVo> getStaticOnDay(Date date);
	
	public List<StaticsReportVo> getStatic(Date beginDate,Date endDate);
}
