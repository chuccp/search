package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.SortField;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class TermSelector extends Selector {
	
	private Map<Integer, TermValue> termValueMap = new LinkedHashMap<>();

	public TermSelector(String[] storeNames) {
		super(storeNames,SortField.Type.INT);
	}
	@Override
	public void collect(int groupId, TermValue termValue) {
		if (mapValue.containsKey(groupId)) {
			mapValue.get(groupId).count();
		} else {
			GroupValue gv = new GroupValue();
			gv.count();
			mapValue.put(groupId, gv);
			termValueMap.put(groupId, termValue);
		}
	}
	
	
	public TermValue getTermValue(Integer groupId) {
		return termValueMap.get(groupId);
	}
	

	public Map<Integer, GroupValue> getMapValue() {
		return mapValue;
	}
}
