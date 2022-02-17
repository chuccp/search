package com.kanke.search.query.selector;

import org.apache.lucene.search.SortField;

import com.kanke.search.query.collector.TermValue;
import com.kanke.search.type.GroupType;

public abstract class Selector {
	
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

}
