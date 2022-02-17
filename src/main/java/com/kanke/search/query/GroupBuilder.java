package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

import com.kanke.search.type.GroupType;

public class GroupBuilder {

	private String[] storeNames;

	private String fieldName;

	private GroupType groupType;

	public GroupBuilder(String[] storeNames) {
		this.storeNames = storeNames;
	}

	public GroupBuilder(String storeNames) {
		this.storeNames = new String[] { storeNames };
	}

	public GroupBuilder fieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	private boolean reverse = false;

	public GroupBuilder desc() {
		reverse = true;
		return this;
	}

	public GroupBuilder asc() {
		reverse = false;
		return this;
	}
	
	public String[] getListStoreNames() {
		List<String> list = new ArrayList<>();
		for(GroupBuilder gb:groupBuilderList) {
			String[] ss = gb.getStoreNames();
			for(String s:ss) {
				list.add(s);
			}
		}
		return list.toArray(new String[] {});
	}

	public String[] getStoreNames() {
		return storeNames;
	}
	
	public String getStoreName() {
		return storeNames[0];
	}

	public String getCountName() {
		return fieldName;
	}

	public boolean isReverse() {
		return reverse;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	private List<GroupBuilder> groupBuilderList = new ArrayList<>();

	public GroupBuilder addGroupBuilder(GroupBuilder groupBuilder) {
		groupBuilderList.add(groupBuilder);
		return this;
	}

	public List<GroupBuilder> getGroupBuilderList() {
		return groupBuilderList;
	}
	
	
	
	
}
