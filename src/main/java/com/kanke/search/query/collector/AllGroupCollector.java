package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SimpleCollector;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;

import com.kanke.search.entry.StoreFileldIndexs;
import com.kanke.search.query.GroupBuilder;
import com.kanke.search.query.selector.Selector;
import com.kanke.search.query.selector.TermSelector;

public class AllGroupCollector extends SimpleCollector {

	private GroupBuilder groupBuilder;

	private StoreFileldIndexs storeFileldIndexs;

	public AllGroupCollector(GroupBuilder groupBuilder, StoreFileldIndexs storeFileldIndexs) {
		this.groupBuilder = groupBuilder;
		this.storeFileldIndexs = storeFileldIndexs;
		this.termSelector = new TermSelector(groupBuilder.getStoreNames());
		this.termSelector.setReverse(groupBuilder.isReverse());
		
		allSelectorMap.put(groupBuilder.getFieldName(), termSelector);
		
		List<GroupBuilder> groupBuilders = this.groupBuilder.getGroupBuilderList();
		for (GroupBuilder group : groupBuilders) {
			Selector selector = Selector.create(group.getGroupType(),storeFileldIndexs.getSortFieldType(group.getStoreName()), group.getStoreNames());
			selector.setReverse(group.isReverse());
			selectorMap.put(group.getFieldName(),selector);
			allSelectorMap.put(group.getFieldName(), selector);
		}
	}

	private GroupDocValues groupDocValues = new GroupDocValues();

	private TermSelector termSelector;

	private Map<String,Selector> selectorMap = new LinkedHashMap<>();

	private Map<String,Selector> allSelectorMap = new LinkedHashMap<>();
	
	private BytesRefHash bytesRefHash = new BytesRefHash();

	@Override
	public ScoreMode scoreMode() {
		return ScoreMode.COMPLETE_NO_SCORES;
	}

	@Override
	public void collect(int doc) throws IOException {
		groupDocValues.advanceExact(doc);
		TermValue termValue = groupDocValues.getTermValue();
		BytesRef bytesRef = termValue.toBytesRef();
		
		int groupId = bytesRefHash.find(bytesRef);
		if (groupId < 0) {
			groupId = bytesRefHash.add(bytesRef);
		}
		termSelector.collect(groupId, termValue);
		for (Selector selector : selectorMap.values()) {
			TermValue tv = groupDocValues.getTermValue(selector.getStoreName());
			selector.collect(groupId, tv);
		}
	}
	
	
	public TermSelector getTermSelector() {
		return termSelector;
	}

	public Map<String, Selector> getAllSelectorMap() {
		return allSelectorMap;
	}
	
	public Map<String, Selector> getSelectorMap() {
		return selectorMap;
	}
	

	@Override
	protected void doSetNextReader(LeafReaderContext context) throws IOException {
		groupDocValues.clear();
		if (groupBuilder.getStoreNames().length > 0) {
			for (String storeName : groupBuilder.getStoreNames()) {
				DocValuesType docValuesType = storeFileldIndexs.getDocValuesType(storeName);
				if (docValuesType == DocValuesType.SORTED) {
					groupDocValues.addSort0(context, storeName);
				} else if (docValuesType == DocValuesType.NUMERIC) {
					groupDocValues.addSortNumeric0(context, storeName);
				}
			}
			for (String storeName : groupBuilder.getListStoreNames()) {
				DocValuesType docValuesType = storeFileldIndexs.getDocValuesType(storeName);
				if (docValuesType == DocValuesType.SORTED) {
					groupDocValues.addSort1(context, storeName);
				} else if (docValuesType == DocValuesType.NUMERIC) {
					groupDocValues.addSortNumeric1(context, storeName);
				}
			}
		}
	}

}
