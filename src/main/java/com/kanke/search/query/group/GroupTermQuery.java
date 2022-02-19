package com.kanke.search.query.group;

import java.util.Map;

import com.kanke.search.query.selector.Selector;

public class GroupTermQuery extends GroupQuery{

	private GroupQueryType groupQueryType;

	private long value;

	private long lowValue;

	private long upValue;

	
	private String fieldName;
	
	
	public GroupTermQuery(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public static GroupTermQuery eq(String fieldName,long value) {
		GroupTermQuery gq = new GroupTermQuery(fieldName);
		gq.value = value;
		gq.groupQueryType = GroupQueryType.EQ;
		return gq;
	}

	/**
	 * 按值范围取
	 * @param lowValue
	 * @param upValue
	 * @return
	 */
	public static GroupTermQuery range(String fieldName,long lowValue, long upValue) {
		GroupTermQuery gq = new GroupTermQuery(fieldName);
		gq.lowValue = lowValue;
		gq.upValue = upValue;
		gq.groupQueryType = GroupQueryType.RANGE;
		return gq;
	}

	public GroupQueryType getGroupQueryType() {
		return groupQueryType;
	}

	public void setGroupQueryType(GroupQueryType groupQueryType) {
		this.groupQueryType = groupQueryType;
	}

	public long getValue() {
		return value;
	}

	public long getLowValue() {
		return lowValue;
	}

	public long getUpValue() {
		return upValue;
	}

	enum GroupQueryType {
		EQ, RANGE
	}


	public String getFieldName() {
		return fieldName;
	}
	
	

	@Override
	public boolean equals(int groupId, Map<String, Selector> selectorMap) {
		Selector  selector  = selectorMap.get(fieldName);
		long value = selector.get(groupId).getValue();
		if(this.groupQueryType == GroupQueryType.RANGE) {
			if(value<=this.upValue&&value>=this.lowValue) {
				return true;
			}
		}else if(this.groupQueryType == GroupQueryType.EQ) {
			if(value==this.value) {
				return true;
			}
		}
		return false;
	}
}
