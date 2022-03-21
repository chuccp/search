package com.kanke.search.query.collector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

public class TermValue {
	
	public TermValue() {
		 this.bytesRefBuilder= new BytesRefBuilder();
		 this.valuesMap = new LinkedHashMap<>();
		 this.valueList = new ArrayList<>();
	}
	
	
	public void addTermValue(TermValue termValue) {
		this.bytesRefBuilder.append(termValue.bytesRefBuilder);
		this.valuesMap.putAll(termValue.valuesMap);
		this.valueList.addAll(termValue.valueList);
		
		
	}
	
	private int docId;

	private BytesRefBuilder bytesRefBuilder;

	
	private Map<String,BytesRefValue> valuesMap;
	
	
	private List<BytesRefValue> valueList;
	
	

	public void addValue(String storeName,BytesRefValue bytesRefValue) {
		this.valuesMap.put(storeName, bytesRefValue);
		this.valueList.add(bytesRefValue);
	}
	
	
	public void addTermValue(String storeName,BytesRefValue bytesRefValue) {
		this.valuesMap.put(storeName, bytesRefValue);
		this.bytesRefBuilder.append(bytesRefValue.getValue());
		this.valueList.add(bytesRefValue);
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


	public String getValue(int num) {
		return this.valueList.get(num).toString();
	}

}
