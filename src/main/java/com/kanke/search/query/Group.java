package com.kanke.search.query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;

public class Group {

	Query query;
	
	private GroupField[] groupFields;
	
	
	private Map<String,GroupField> groupFieldMap = new LinkedHashMap<>();
	
	public GroupField getGroupField(String index) {
		return groupFieldMap.get(index);
	}
	public List<GroupField> getOtherGroupFields(String index) {
		List<GroupField> list = new ArrayList<GroupField>();
		for (Map.Entry<String,GroupField> entry : groupFieldMap.entrySet()) {
			String key = entry.getKey();
			if(StringUtils.equalsIgnoreCase(index, key)) {
				continue;
			}
			GroupField val = entry.getValue();
			list.add(val);
		}
		return list;
	}
	
	

	public GroupField[] getGroupFields() {
		return groupFields;
	}

	public void setGroupFields(GroupField[] groupFields) {
		this.groupFields = groupFields;
	
	}
	
	public void initMap() {
		for(GroupField gf:groupFields) {
			groupFieldMap.put(gf.getIndex(), gf);
		}
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
		GroupField groupField = new GroupField();
		groupField.setStoreNames(storeName);
		return term(groupField);
	}
	public static Group term(GroupField... groupFields) {
		Group group = new Group();
		group.groupFields = groupFields;
		return group;
	}
	
	private List<GroupBuilder.Report> list = new ArrayList<>();
	
	public void addReport(GroupBuilder.Report report) {
		list.add(report);
	}

	public List<GroupBuilder.Report> getReports() {
		return list;
	}
	
	
	
	
	

}
