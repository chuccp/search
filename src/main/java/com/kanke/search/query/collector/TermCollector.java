package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SimpleCollector;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;

public class TermCollector extends SimpleCollector {

	private String[] storeNames;

	private List<SortedDocValues> sortedDocValuesList = new ArrayList<SortedDocValues>();

	private Map<Integer, GroupValue> mapValue = new LinkedHashMap<>();

	private BytesRefHash bytesRefHash = new BytesRefHash();

	public TermCollector(String... storeName) {
		this.storeNames = storeName;
	}

	@Override
	public ScoreMode scoreMode() {
		return ScoreMode.COMPLETE_NO_SCORES;
	}

	@Override
	public void collect(int doc) throws IOException {
		for (SortedDocValues sortedDocValues : sortedDocValuesList) {
			if (doc > sortedDocValues.docID()) {
				sortedDocValues.advanceExact(doc);
			}
		}
		TermValue termValue = new TermValue(sortedDocValuesList.size());
		for (SortedDocValues sortedDocValues : sortedDocValuesList) {
			int ord = sortedDocValues.ordValue();
			termValue.addValue(ord, sortedDocValues.binaryValue());
		}
		BytesRef value = termValue.toBytesRef();
		int num = bytesRefHash.find(value);
		if (num < 0) {
			num = bytesRefHash.add(value);
		}
		if (mapValue.containsKey(num)) {
			mapValue.get(num).add();
		} else {
			GroupValue gv = new GroupValue(termValue, 1);
			mapValue.put(num, gv);
		}
	}

	public Map<Integer, GroupValue> getGroupValue() {
		return mapValue;
	}

	@Override
	protected void doSetNextReader(LeafReaderContext context) throws IOException {
		sortedDocValuesList.clear();
		if (storeNames.length > 0) {
			for (String storeName : storeNames) {
				sortedDocValuesList.add(DocValues.getSorted(context.reader(), storeName));
			}
		}
	}

}
