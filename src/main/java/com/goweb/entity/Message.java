package com.goweb.entity;

import java.util.Date;

/**
 * 
 * 日志消息
 * 
 * 
 *
 */

public class Message extends BaseEntity {

	private String destAddr;

	private String oriAddr;

	private String msgType;

	private String msgContent;

	private Date logDate;

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

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

}
