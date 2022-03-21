package com.kanke.search.query.collector;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

public class TermValue {
	
	public TermValue() {
		 this.bytesRefBuilder= new BytesRefBuilder();
		 this.valuesMap = new HashMap<>();
	}
	
	
	public void addTermValue(TermValue termValue) {
		this.bytesRefBuilder.append(termValue.bytesRefBuilder);
		this.valuesMap.putAll(termValue.valuesMap);
		
	}
	
	private int docId;

	private BytesRefBuilder bytesRefBuilder;

	
	private Map<String,BytesRefValue> valuesMap;

	public void addValue(String storeName,BytesRefValue bytesRefValue) {
		valuesMap.put(storeName, bytesRefValue);

	}
	
	
	public void addTermValue(String storeName,BytesRefValue bytesRefValue) {
		valuesMap.put(storeName, bytesRefValue);
		bytesRefBuilder.append(bytesRefValue.getValue());
	}

	public BytesRef toBytesRef() {
		return bytesRefBuilder.toBytesRef();
	}

	public int getSize() {
		return valuesMap.size();
	}

	public BytesRefValue get(String storeName) {
		return valuesMap.get(storeName);
	}
	
	public String getValue(String storeName) {
		BytesRefValue v  = get(storeName);
		return v.toString();
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

}
