package com.goweb.service;

import java.io.Serializable;

import com.goweb.model.page.Page;
import com.goweb.model.page.PageParemeter;

/**
 * 
 * Service层分页基类接口
 * 
 * 
 *
 * @param <T>
 * @param <ID>
 * 
 */
public interface IPageBaseService<T,ID extends Serializable> extends IBaseService<T,ID> {
	
	public Page<T> findAll(PageParemeter pageParemeter) ;
	
}
