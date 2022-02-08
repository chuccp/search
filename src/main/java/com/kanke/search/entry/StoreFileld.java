package com.kanke.search.entry;

import java.lang.reflect.Field;

public class StoreFileld {
	
	
	private Field field;
	
	private String FieldName; 

	private String storeName; 
	
	private boolean isId;
	
	private boolean isSort;

	
	

	public boolean isSort() {
		return isSort;
	}


	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}


	public Field getField() {
		return field;
	}


	public void setField(Field field) {
		this.field = field;
	}


	public String getFieldName() {
		return FieldName;
	}


	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public boolean isId() {
		return isId;
	}


	public void setId(boolean isId) {
		this.isId = isId;
	}
	
	
	
	

}
