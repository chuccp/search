package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class SumSelector extends Selector {

	private Map<Integer, GroupValue> mapValue = new LinkedHashMap<>();

	public SumSelector(String[] storeNames, Type sortFieldType) {
		super(storeNames, sortFieldType);
	}

	@Override
	public void collect(int groupId, TermValue termValue) {
		BytesRef bytesRef = termValue.get(0);
		long num = NumericUtils.sortableBytesToLong(bytesRef.bytes,0);
		if(!mapValue.containsKey(groupId)) {
			mapValue.put(groupId, new GroupValue(termValue));
		}
		mapValue.get(groupId).sum(num);
	}

}
