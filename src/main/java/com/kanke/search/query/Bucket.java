package com.kanke.search.query;

import com.kanke.search.query.collector.TermValue;

public abstract class Bucket {
	
	private int groupId;
	
	
	public Bucket(int groupId) {
		this.groupId = groupId;
	}


	public int getGroupId() {
		return groupId;
	}


	public abstract String getStoreName(int num);



	public abstract TermValue getFieldValue(String name);


}
