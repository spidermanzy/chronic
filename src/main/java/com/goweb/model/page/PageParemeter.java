package com.goweb.model.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 分页参数
 * 
 * @author yinsheng
 *	
 */
public class PageParemeter{
	
	private int pageCount;
	
	private int pageNumber;
	
	private String orderColumn; 
	
	private Order  orderValue;
	
	
	private Map<String,Order> orderMap = null;
	
	private String searchColumn;
	
	private String searchValue;
	
	public PageParemeter(int pageCount,int pageNumber,String orderColumn,Order orderValue,String searchColumn,String searchValue,Map<String,Order> orderMap){
		this.pageCount = pageCount;
		this.pageNumber = pageNumber;
		this.orderColumn = orderColumn;
		this.orderValue = orderValue;
		this.searchColumn = searchColumn;
		this.searchValue = searchValue;
		this.orderMap = orderMap;
	}
	
	public PageParemeter(int pageCount,int pageNumber,String orderColumn,Order orderValue,String searchColumn,String searchValue){
		this(pageCount,pageNumber,orderColumn,orderValue,searchColumn,searchValue,null);
	}
	
	public PageParemeter(){}
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public Order getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Order orderValue) {
		this.orderValue = orderValue;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public Map<String, Order> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Order> orderMap) {
		this.orderMap = orderMap;
	}
	
	
	
}
