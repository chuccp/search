package com.kanke.search.query;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;

import com.kanke.search.query.collector.TermCollector;


public class Group {

	Query query;

	private TermCollector termCollector;
	
	
	
	public TermCollector getTermCollector() {
		return termCollector;
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

	private SortField sortField = new SortField(null, Type.DOC, true);

	public SortField getSortField() {
		return sortField;
	}

	public Group desc() {
		return this;
	}

	public Group asc() {
		sortField = new SortField(null, Type.DOC, false);
		return this;
	}

	public static Group term(String ...storeName) {
		Group group = new Group();
		group.termCollector = new TermCollector(storeName);
		return group;
	}

}
