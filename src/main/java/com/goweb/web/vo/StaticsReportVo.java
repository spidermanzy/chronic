package com.goweb.web.vo;

import java.util.Date;

/**
 * 
 * 报表统计信息VO
 * 
 * @author yinsheng
 *	
 */
public class StaticsReportVo {
	
	private String destAddr;
	
	private String oriAddr;
	
	private String msgType;
	
	private String date;
	
	private Long count;

	public String getDestAddr() {
		return destAddr;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}

	public String getOriAddr() {
		return oriAddr;
	}

	public void setOriAddr(String oriAddr) {
		this.oriAddr = oriAddr;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	
	
	
}
