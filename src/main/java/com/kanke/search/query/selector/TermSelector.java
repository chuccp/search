package com.kanke.search.query.selector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;

import com.kanke.search.query.collector.TermValue;

public class TermSelector  {
	
	
	private Map<Integer,TermValue> termMap = new LinkedHashMap<>();
	
	
	
	
	private List<Selector> selectorList;
	public TermSelector() {
		this.selectorList = new ArrayList<>();
	}
	
	public void AddSelector(Selector selector) {
		this.selectorList.add(selector);
	}
	private BytesRefHash bytesRefHash = new BytesRefHash();

	public void collect(TermValue termValue) {
		BytesRef bytesRef = termValue.toBytesRef();
		int groupId = bytesRefHash.find(bytesRef);
		if (groupId < 0) {
			groupId = bytesRefHash.add(bytesRef);
		}
		this.collect(groupId, termValue);
	}
	public void collect(int groupId, TermValue termValue) {
		if(!termMap.containsKey(groupId)) {
			termMap.put(groupId, termValue);
		}
		for(Selector selector:selectorList) {
			selector.collect(groupId, termValue);
		}
	}
	
	
	
	
}
