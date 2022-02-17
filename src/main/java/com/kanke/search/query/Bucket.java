package com.kanke.search.query;

import com.kanke.search.query.collector.GroupValue;

public class Bucket {
	
	public Bucket(GroupValue groupValue) {
		this.groupValue = groupValue;
	}

	private GroupValue groupValue;

//	public Long getAmouts(String name) {
//		return groupValue.getCount();
//	}
//
//
//
//	public Long getDoc_count() {
//		return groupValue.getCount();
//	}



	public String getFieldValue(String name) {
		return null;
	}


}
