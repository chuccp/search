package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.SortedDocValues;

import com.kanke.search.query.GroupField;

public class GroupDocValues {
	
	
	
	private GroupField  groupField;

	public GroupDocValues(GroupField groupFields) {
		this.groupField = groupFields;
	}

	private List<DocValue> docValuesList = new ArrayList<>();

	public void addSort(LeafReaderContext context, String storeName) throws IOException {
		FieldInfo fieldInfo = context.reader().getFieldInfos().fieldInfo(storeName);
		if (fieldInfo != null) {
			if (fieldInfo.getDocValuesType() == DocValuesType.NUMERIC) {
				NumericDocValues numericDocValues = DocValues.getNumeric(context.reader(), storeName);
				docValuesList.add(new DocValue(numericDocValues,storeName));
			} else if (fieldInfo.getDocValuesType() == DocValuesType.SORTED) {
				SortedDocValues sortedDocValues = DocValues.getSorted(context.reader(), storeName);
				docValuesList.add(new DocValue(sortedDocValues,storeName));
			}
		}
	}

	public void advanceExact(int doc) throws IOException {
		for (DocValue docValue : docValuesList) {
			docValue.advanceExact(doc);
		}
	}

	public void clear() {
		docValuesList.clear();
	}

	public TermValue getTermValue() throws IOException {
		TermValue termValue = new TermValue();
		for (DocValue docValue : docValuesList) {
			if(StringUtils.equalsAnyIgnoreCase(docValue.getSortName(),groupField.getStoreNames())) {
				termValue.addTermValue(docValue.getSortName()  ,docValue.value());
			}else {
				termValue.addValue(docValue.getSortName()  ,docValue.value());
			}
		}
		return termValue;
	}
}
