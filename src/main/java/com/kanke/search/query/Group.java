package com.kanke.search.query;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.grouping.GroupSelector;
import org.apache.lucene.search.grouping.TermGroupSelector;

public class Group {

	Query query;

	private GroupSelector<?> groupSelector;

	public GroupSelector<?> getGroupSelector() {
		return groupSelector;
	}

	public void setGroupSelector(GroupSelector<?> groupSelector) {
		this.groupSelector = groupSelector;
	}

	private int offset;

	private int limit = 100;

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public static Group term(String storeName) {
		Group group = new Group();
		group.groupSelector =new TermGroupSelector(storeName);
		return group;
	}
}
