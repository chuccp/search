package com.kanke.search.query;

public class GroupBuilders {

	public GroupBuilder groupBy(String... fieldName) {
		return new GroupBuilder(fieldName);
	}
	
	/**
	 * 算最大值
	 * @param fieldName
	 * @return
	 */
	
	public GroupBuilder max(String fieldName) {
		return null;
	}
	
	
	

}
