package com.goweb.disease.service;

import com.goweb.disease.entity.Log;
import com.goweb.disease.entity.Message;

/**
 * 
 * 日志服务接口类
 * 
 * @author yinsheng
 *
 */

public interface ILogService extends IBaseService<Log, String> {
	
	public Log findOne(String property,Object value);
	
	public void updateSingleLogContent(String id, String logContent);
	
	public void update(Log log);
}
