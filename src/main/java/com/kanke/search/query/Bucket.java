package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

public class Bucket {

	private Object key;
	
	private Long doc_count;
	
	private List<Long> amounts = new ArrayList<Long>();
	
	
	public Long getAmouts(String name) {
		return amounts.get(0);
	}
	
	
	public Object get() {
		return amounts;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Long getDoc_count() {
		return doc_count;
	}

	public void setDoc_count(Long doc_count) {
		this.doc_count = doc_count;
	}
	
	
	public List<Long> getAmounts() {
		return amounts;
	}
	
	

	public void addAmounts(Long value) {
		this.amounts.add(value);
	}
	
}
