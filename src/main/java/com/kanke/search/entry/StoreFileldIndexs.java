package com.kanke.search.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.search.SortField;

public class StoreFileldIndexs {

	private StoreFileldIndexs() {
	}

	private List<StoreFileldIndex> storeFileldIndexlist;

	public static StoreFileldIndexs CreateStoreFileldIndexs() {
		StoreFileldIndexs storeFileldIndexs = new StoreFileldIndexs();
		storeFileldIndexs.storeFileldIndexlist = new ArrayList<StoreFileldIndex>();
		return storeFileldIndexs;

	}

	public void addStoreFileldIndex(StoreFileldIndex storeFileldIndex) {
		storeFileldIndexlist.add(storeFileldIndex);
	}

	public boolean isEmpty() {
		return this.storeFileldIndexlist.isEmpty();
	}

	private Map<String, StoreFileldIndex> storeFileldIndexMap = new HashMap<>();

	public StoreFileldIndex get(String storeName) {
		if (storeFileldIndexMap.containsKey(storeName)) {
			return storeFileldIndexMap.get(storeName);
		}
		for (StoreFileldIndex storeFileldIndex : storeFileldIndexlist) {
			storeFileldIndexMap.put(storeFileldIndex.getStoreName(), storeFileldIndex);
		}
		return storeFileldIndexMap.get(storeName);
	}

	public DocValuesType getDocValuesType(String storeName) {
		StoreFileldIndex storeFileldIndex = this.get(storeName);
		String genericType = storeFileldIndex.getGenericType();
		if (StringUtils.equals(genericType, "java.lang.String")) {
			return DocValuesType.SORTED;
		} else {
			return DocValuesType.NUMERIC;
		}
	}

	public SortField.Type getSortFieldType(String storeName) {
		StoreFileldIndex storeFileldIndex = this.get(storeName);
		String genericType = storeFileldIndex.getGenericType();
		if (StringUtils.equals(genericType, "java.lang.Long")) {
			return SortField.Type.LONG;
		} else if (StringUtils.equals(genericType, "java.lang.Float")) {
			return SortField.Type.FLOAT;
		} else if (StringUtils.equals(genericType, "java.lang.Integer")) {
			return SortField.Type.INT;
		} else {
			return SortField.Type.DOC;
		}
	}

}
