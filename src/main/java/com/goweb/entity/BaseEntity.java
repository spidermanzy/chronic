package com.goweb.entity;

import java.util.Date;

/**
 * 
 * 实体基类
 * 
 * 
 *
 */

public class BaseEntity {

	public BaseEntity() {
		createDate = new Date();
	}

	public String id;

	/**
	 * 创建时间
	 */
	public Date createDate;

	public Date getCreateDate() {

		return createDate;
	}

	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
