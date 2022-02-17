package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.SortField;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class TermSelector extends Selector {

	public TermSelector(String[] storeNames) {
		super(storeNames,SortField.Type.INT);
	}

	private Map<Integer, GroupValue> mapValue = new LinkedHashMap<>();

	@Override
	public void collect(int groupId, TermValue termValue) {
		if (mapValue.containsKey(groupId)) {
			mapValue.get(groupId).count();
		} else {
			GroupValue gv = new GroupValue(termValue);
			gv.count();
			mapValue.put(groupId, gv);
		}
	}
}
