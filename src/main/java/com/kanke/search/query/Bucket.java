package com.kanke.search.query;

import com.kanke.search.query.collector.GroupValue;

public class Bucket {
	
	public Bucket(GroupValue groupValue) {
		this.groupValue = groupValue;
	}

	private GroupValue groupValue;

	public Long getAmouts(String name) {
		return groupValue.getCount();
	}

	public String get(int index) {
		return groupValue.getTermValue().get(index).utf8ToString();
	}

	public Long getDoc_count() {
		return groupValue.getCount();
	}

	public String getKey() {
		return get(0);
	}

}
