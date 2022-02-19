package com.kanke.search.query;

import com.kanke.search.query.group.GroupTermQuery;
import com.kanke.search.type.GroupType;

public class GroupBuilders {

	public static GroupBuilder groupBy(String... fieldName) {
		return groupBy(null, fieldName);
	}

	public static GroupBuilder groupBy(GroupTermQuery groupQuery, String... fieldName) {
		GroupBuilder groupBuilder = new GroupBuilder(fieldName);
		groupBuilder.setGroupType(GroupType.COUNT);
		return groupBuilder;
	}

	/**
	 * 算最大值
	 * 
	 * @param fieldName
	 * @return
	 */

	public static GroupBuilder sum(String fieldName) {
		GroupBuilder groupBuilder = new GroupBuilder(fieldName);
		groupBuilder.setGroupType(GroupType.SUM);
		return groupBuilder;
	}


	public static GroupBuilder max(String fieldName) {
		GroupBuilder groupBuilder = new GroupBuilder(fieldName);
		groupBuilder.setGroupType(GroupType.MAX);
		return groupBuilder;
	}



	public static GroupBuilder min(String fieldName) {
		GroupBuilder groupBuilder = new GroupBuilder(fieldName);
		groupBuilder.setGroupType(GroupType.MIN);
		return groupBuilder;
	}


	public static GroupBuilder avg(String fieldName) {
		GroupBuilder groupBuilder = new GroupBuilder(fieldName);
		groupBuilder.setGroupType(GroupType.AVG);
		return groupBuilder;
	}


}
