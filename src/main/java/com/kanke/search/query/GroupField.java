package com.kanke.search.query;

public class GroupField {
	
	private String index;
	
	
	private String[] storeNames;


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}


	public String[] getStoreNames() {
		return storeNames;
	}


	public void setStoreNames(String[] storeNames) {
		this.storeNames = storeNames;
	}
	
	
	public static GroupField createGroupField(String index,String ...storeNames) {
		GroupField groupField = new GroupField();
		groupField.index = index;
		groupField.storeNames = storeNames;
		return groupField;
		
	}
	
	

}
