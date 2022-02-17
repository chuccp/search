package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.index.SortedNumericDocValues;

public class GroupDocValues {

	private List<SortedDocValues> sortedDocValuesList0 = new ArrayList<SortedDocValues>();

	private List<SortedNumericDocValues> sortedNumericDocValuesList0 = new ArrayList<SortedNumericDocValues>();

	private List<SortedDocValues> sortedDocValuesList1 = new ArrayList<SortedDocValues>();

	private List<NumericDocValues> numericDocValues1 = new ArrayList<NumericDocValues>();
	
	private Map<String,NumericDocValues> numericDocValuesMap1 = new HashMap<>();
	
	private Map<String,SortedDocValues> sortedDocValuesMap1 = new HashMap<>();
	

	public void addSort0(LeafReaderContext context, String storeName) throws IOException {
			sortedDocValuesList0.add(DocValues.getSorted(context.reader(), storeName));
		
	}

	public void addSortNumeric0(LeafReaderContext context, String storeName) throws IOException {
		
			sortedNumericDocValuesList0.add(DocValues.getSortedNumeric(context.reader(), storeName));
		
	}

	public void addSort1(LeafReaderContext context, String storeName) throws IOException {
			SortedDocValues  sortedDocValues  = DocValues.getSorted(context.reader(), storeName);
			sortedDocValuesList1.add(sortedDocValues);
			sortedDocValuesMap1.put(storeName, sortedDocValues);
			
		
	}

	public void addSortNumeric1(LeafReaderContext context, String storeName) throws IOException {
			NumericDocValues  numericDocValues  = DocValues.getNumeric(context.reader(), storeName);
			numericDocValues1.add(numericDocValues);
			numericDocValuesMap1.put(storeName, numericDocValues);
			
	
	}

	public void clear() {
		sortedDocValuesList0.clear();
		sortedNumericDocValuesList0.clear();
		sortedDocValuesList1.clear();
		numericDocValues1.clear();
		numericDocValuesMap1.clear();
		sortedDocValuesMap1.clear();
	}
	
	
	public TermValue getTermValue() throws IOException {
		TermValue termValue = new TermValue();
		for (SortedDocValues sortedDocValues : sortedDocValuesList0) {
				termValue.addValue(sortedDocValues.binaryValue());
		}
		for (NumericDocValues sortedDocValues : numericDocValues1) {
				termValue.addValue(sortedDocValues.longValue());
		}
		return termValue;
	}
	
	
	public TermValue getNumericValue(String storeName) throws IOException {
		TermValue termValue = new NumericTermValue();
		NumericDocValues numericDocValues = numericDocValuesMap1.get(storeName);
		long v = numericDocValues.longValue();
		termValue.addValue(v);
		return termValue;
	}
	
	public TermValue getTermValue(String storeName) throws IOException {
		if(numericDocValuesMap1.containsKey(storeName)) {
			TermValue termValue = new NumericTermValue();
			NumericDocValues numericDocValues = numericDocValuesMap1.get(storeName);
			long v = numericDocValues.longValue();
			termValue.addValue(v);
			return termValue;
		}else {
			TermValue termValue = new TermValue();
			SortedDocValues sortedDocValues = sortedDocValuesMap1.get(storeName);
			termValue.addValue(sortedDocValues.binaryValue());
			return termValue;
		}
	}
	

	public void advanceExact(int doc) throws IOException {
		for (SortedDocValues sortedDocValues : sortedDocValuesList0) {
			if (doc > sortedDocValues.docID()) {
					sortedDocValues.advanceExact(doc);
			}

		}
		for (SortedNumericDocValues sortedDocValues : sortedNumericDocValuesList0) {
			if (doc > sortedDocValues.docID()) {
					sortedDocValues.advanceExact(doc);
			}

		}
		for (SortedDocValues sortedDocValues : sortedDocValuesList1) {
			if (doc > sortedDocValues.docID()) {
					sortedDocValues.advanceExact(doc);
			}

		}
		for (NumericDocValues sortedDocValues : numericDocValues1) {
			if (doc > sortedDocValues.docID()) {
					sortedDocValues.advanceExact(doc);
			}

		}
	}

}
