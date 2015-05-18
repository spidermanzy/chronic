package com.goweb.disease.web.vo;

import java.util.Date;

/**
 * 
 * 监控数据
 * 
 * @author yinsheng
 *	
 */
public class RealTimeDataVo {
	
	private Date monitor;
	
	private Long count;

	public Date getMonitor() {
		return monitor;
	}

	public void setMonitor(Date monitor) {
		this.monitor = monitor;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
	
}
