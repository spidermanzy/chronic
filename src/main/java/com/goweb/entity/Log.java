package com.goweb.entity;

/**
 * 
 * 日志
 * 
 * 
 *
 */

public class Log extends BaseEntity {

	private String logType;

	private String content;

	private String comment;
	
	private String firstline;

	private long offset;

	private int offsetCount;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getOffsetCount() {
		return offsetCount;
	}

	public void setOffsetCount(int offsetCount) {
		this.offsetCount = offsetCount;
	}

	public String getFirstline() {
		return firstline;
	}

	public void setFirstline(String firstlin) {
		this.firstline = firstlin;
	}

}
