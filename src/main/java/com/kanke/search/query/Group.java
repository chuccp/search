package com.kanke.search.query;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.Query;

import com.kanke.search.query.collector.AllGroupCollector;
import com.kanke.search.type.GroupType;

public class Group {

	Query query;

	private AllGroupCollector allGroupCollector;

	public AllGroupCollector getAllGroupCollector() {
		return allGroupCollector;
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

	private Map<GroupType, String> groupTypeMap = new LinkedHashMap<>();

	public Group count(String aliasName) {
		groupTypeMap.put(GroupType.COUNT, aliasName);
		return this;
	}

	public Map<GroupType, String> getGroupTypeMap() {
		return groupTypeMap;
	}

	private boolean reverse = false;

	public Group desc() {
		reverse = true;
		return this;
	}

	public Group asc() {
		reverse = false;
		return this;
	}

	public boolean isReverse() {
		return reverse;
	}

	public static Group term(String... storeName) {
		Group group = new Group();
		return group;
	}

}
