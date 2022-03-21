package com.kanke.search.query.collector;

import java.util.HashMap;
import java.util.Map;

public class TermValueCache {
	
	private Map<String,TermValue>  termValueMap = new HashMap<>();
	
	public void put(String index,String fieldName,BytesRefValue bytesRefValue,TermValue termValue) {
		String key  = index+"$$$"+fieldName+"$$$"+bytesRefValue.toString();
		if(!termValueMap.containsKey(key)) {
			termValueMap.put(key, termValue);
		}
	}
	
	public TermValue get(String index,String fieldName,BytesRefValue bytesRefValue) {
		String key  = index+"$$$"+fieldName+"$$$"+bytesRefValue.toString();
		return termValueMap.get(key);
	}
}
