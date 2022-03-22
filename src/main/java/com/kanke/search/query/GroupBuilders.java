package com.kanke.search.query;

import com.kanke.search.query.group.GroupTermQuery;

public class GroupBuilders {

	public static GroupBuilder groupBy(GroupField... groupFields) {
		return groupBy(null, groupFields);
	}

	public static GroupBuilder groupBy(GroupTermQuery groupQuery, GroupField... groupFields) {
		GroupBuilder groupBuilder = new GroupBuilder(groupFields);
		groupBuilder.setGroupQuery(groupQuery);
		return groupBuilder;
	}

	public static GroupBuilder groupBy(String storeName) {
		return groupBy(GroupField.createGroupField(null, storeName));
	}

	public static GroupBuilder sum(String storeName) {
		GroupBuilder groupBuilder = new GroupBuilder();
		groupBuilder.sum(GroupField.createGroupField(null, storeName), null);
		return groupBuilder;
	}

}
