package com.goweb.disease.dao;

import java.util.Date;
import java.util.List;

import com.goweb.disease.entity.Message;
import com.goweb.disease.web.vo.StaticsReportVo;

/**
 * 
 * 日志消息接口
 * 
 * @author yinsheng
 *
 */
public interface IMessageDao extends IBaseDao{

	// 获取一天的数据
	List<Message> getMessageOnDay(Date date);

	List<Message> getMessage(Date beginDate, Date endDate);

	List<Message> getMessageByLogDate();

	List<StaticsReportVo> getStaticOnDay(Date date);

	List<StaticsReportVo> getStatic(Date beginDate, Date endDate);
}
