package com.goweb.disease.service;

import java.io.Serializable;

import com.goweb.disease.model.page.Page;
import com.goweb.disease.model.page.PageParemeter;

/**
 * 
 * Service层分页基类接口
 * 
 * @author yinsheng
 *
 * @param <T>
 * @param <ID>
 * 
 */
public interface IPageBaseService<T,ID extends Serializable> extends IBaseService<T,ID> {
	
	public Page<T> findAll(PageParemeter pageParemeter) ;
	
}
