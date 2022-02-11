package com.kanke.search.entry;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.lucene.search.SortField;

public class StoreFileld {

	private Field field;

	private String fieldName;

	private String sortName;

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
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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

	public SortField.Type getSortType() {
		Type type = field.getGenericType();
		if (type == Long.class ||type == Date.class) {
			return SortField.Type.LONG;
		}
		if (type == Integer.class) {
			return SortField.Type.INT;
		}
		return SortField.Type.DOC;
	}

	public String getSortName() {
		if(sortName==null) {
			sortName = this.fieldName + "_" + this.getSortType().name();
		}
		return sortName;
	}

}
