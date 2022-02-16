package com.kanke.search.query;

public class GroupBuilder {

	private String[] storeNames;

	private String fieldName;

	public GroupBuilder(String[] storeNames) {
		this.storeNames = storeNames;
	}

	public GroupBuilder FieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	private boolean reverse = false;

	public GroupBuilder desc() {
		reverse = true;
		return this;
	}

	public GroupBuilder asc() {
		reverse = false;
		return this;
	}

	public String[] getStoreNames() {
		return storeNames;
	}

	public String getCountName() {
		return fieldName;
	}

	public boolean isReverse() {
		return reverse;
	}

}
