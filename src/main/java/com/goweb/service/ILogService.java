package com.goweb.service;

import com.goweb.entity.Log;
import com.goweb.entity.Message;

/**
 * 
 * 日志服务接口类
 * 
 * 
 *
 */

public interface ILogService extends IBaseService<Log, String> {
	
	public Log findOne(String property,Object value);
	
	public void updateSingleLogContent(String id, String logContent);
	
	public void update(Log log);
}
