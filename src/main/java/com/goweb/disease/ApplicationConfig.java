package com.goweb.disease;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用配置
 * @author yinsheng
 *
 */
public class ApplicationConfig {
	
	/**
	 * 最新监听数据间隔 单位：分钟
	 */
	private int lastestDataInterval;
	
	/**
	 * 数据监听间隔 单位：分钟
	 */
	private int dataMonitorInterval;
	
	private String datetimeFormat;
	
	private String dateFormat;
	
	public List<String> clientLogTypeList = new ArrayList<String>();
	
	public int getLastestDataInterval() {
		return lastestDataInterval;
	}

	public void setLastestDataInterval(int lastestDataInterval) {
		this.lastestDataInterval = lastestDataInterval;
	}

	public int getDataMonitorInterval() {
		return dataMonitorInterval;
	}

	public void setDataMonitorInterval(int dataMonitorInterval) {
		this.dataMonitorInterval = dataMonitorInterval;
	}

	public String getDatetimeFormat() {
		return datetimeFormat;
	}

	public void setDatetimeFormat(String datetimeFormat) {
		this.datetimeFormat = datetimeFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<String> getClientLogTypeList() {
		return clientLogTypeList;
	}

	public void setClientLogTypeList(List<String> clientLogTypeList) {
		this.clientLogTypeList = clientLogTypeList;
	}

	
}
