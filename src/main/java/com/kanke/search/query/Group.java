package com.kanke.search.query;

import org.apache.lucene.search.Query;

public class Group {

	Query query;


	private String[] storeNames;


	public String[] getStoreNames() {
		return storeNames;
	}

	public void setStoreNames(String[] storeNames) {
		this.storeNames = storeNames;
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

	private String aliasName;

	public Group count(String aliasName) {
		this.aliasName = aliasName;
		return this;
	}

	public String getAliasName() {
		return aliasName;
	}

	private boolean reverse = false;
	
	private boolean order = false;

	public Group desc() {
		reverse = true;
		order = true;
		return this;
	}

	public Group asc() {
		reverse = false;
		order = true;
		return this;
	}

	public boolean isReverse() {
		return reverse;
	}
	

	public boolean isOrder() {
		return order;
	}

	public static Group term(String... storeName) {
		Group group = new Group();
		group.storeNames = storeName;
		return group;
	}

}
