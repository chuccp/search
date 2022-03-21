package com.kanke.search.query.collector;

import java.io.IOException;

import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class DocValue {
	
	private String sortName;
	
	

	public String getSortName() {
		return sortName;
	}

	public DocValue(SortedDocValues sortedDocValues,String sortName) {
		this.sortedDocValues = sortedDocValues;
		this.docValuesType = DocValuesType.SORTED;
		this.sortName = sortName;
	}

	public DocValue(NumericDocValues numericDocValues,String sortName) {
		this.numericDocValues = numericDocValues;
		this.docValuesType = DocValuesType.NUMERIC;
		this.sortName = sortName;
	}

	private NumericDocValues numericDocValues;

	private SortedDocValues sortedDocValues;

	private DocValuesType docValuesType;
	
	public BytesRefValue value() throws IOException {
		if (docValuesType == DocValuesType.NUMERIC) {
			byte[] data = new byte[8];
			NumericUtils.longToSortableBytes(numericDocValues.longValue(), data, 0);
			BytesRef bytesRef = new BytesRef(data);
			return new BytesRefValue(bytesRef, docValuesType);
		} else if(docValuesType == DocValuesType.SORTED) {
			return new BytesRefValue(BytesRef.deepCopyOf(sortedDocValues.binaryValue()), docValuesType);
		}
		return null;
	}
	public DocValuesType getDocValuesType() {
		return docValuesType;
	}

	public void setDocValuesType(DocValuesType docValuesType) {
		this.docValuesType = docValuesType;
	}

	public void advanceExact(int doc) throws IOException {
		if (docValuesType == DocValuesType.NUMERIC) {
			if (doc > numericDocValues.docID()) {
				numericDocValues.advanceExact(doc);
			}
		}else if(docValuesType == DocValuesType.SORTED) {
			if (doc > sortedDocValues.docID()) {
				sortedDocValues.advanceExact(doc);
			}
		}
	}

}
