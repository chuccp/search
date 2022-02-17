package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.SortField;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;
import com.kanke.search.type.GroupType;

public abstract class Selector {
	
	protected Map<Integer, GroupValue> mapValue = new LinkedHashMap<>();
	
	private boolean reverse = false;
	
	private SortField.Type sortFieldType;
	
	public SortField.Type getSortFieldType() {
		return sortFieldType;
	}

	private String[] storeNames;
	

	public Selector(String[] storeNames,SortField.Type sortFieldType) {
		this.storeNames = storeNames;
		this.sortFieldType = sortFieldType;
	}

	public abstract void collect(int doc, TermValue termValue);

	public static Selector create(GroupType groupType,SortField.Type sortFieldType,String[] storeNames) {
		if(groupType==GroupType.SUM) {
			SumSelector sumSelector =  new SumSelector(storeNames,sortFieldType);
			return sumSelector;
		}
		return null;
	}

	public String[] getStoreNames() {
		return this.storeNames;
	}
	public String getStoreName() {
		return this.storeNames[0];
	}
	public GroupValue get(Integer groupId ) {
		return mapValue.get(groupId);
	}
	

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

}
