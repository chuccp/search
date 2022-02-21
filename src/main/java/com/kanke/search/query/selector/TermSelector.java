package com.kanke.search.query.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.search.SortField;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class TermSelector extends Selector {
	
	private Map<Integer, TermValue> termValueMap = new LinkedHashMap<>();
	
	
	private Map<Integer, Set<Integer>> docValueMap = new LinkedHashMap<>();

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
	
	public void addDocId(int groupId, Integer docId) {
		if(!docValueMap.containsKey(groupId)) {
			docValueMap.put(groupId, new HashSet<>());
		}
		docValueMap.get(groupId).add(docId);
		
		
	}
	
	public TermValue getTermValue(Integer groupId) {
		return termValueMap.get(groupId);
	}
	
	public List<Integer> getDocIdList(Integer groupId) {
		return new ArrayList<Integer>(docValueMap.get(groupId));
	}
	public Integer[] getDocIds(Integer groupId) {
		return docValueMap.get(groupId).toArray(new Integer[] {});
	}
	public Map<Integer, GroupValue> getMapValue() {
		return mapValue;
	}
}
