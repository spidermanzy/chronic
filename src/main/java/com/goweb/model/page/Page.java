package com.goweb.model.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 页
 * 
 * 
 *	
 */
public class Page<T> {
	
	private int pageCount;
	
	private int pageNumber;

	
	private long totalCount;
	
	private List<T> elements = null;
	
	public Page(int pageCount,int pageNumber,long totalCount,List<T> elements){
		this.elements = elements;
		this.pageCount =pageCount;
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
	};
	
	
	public Page(int pageCount,int pageNumber,long totalCount){
		this(pageCount,pageNumber,totalCount,null);
	}
	
	
	
	public Page(){
	
	}
	
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	
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
	
	
}
