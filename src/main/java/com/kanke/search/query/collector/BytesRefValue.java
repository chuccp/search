package com.kanke.search.query.collector;

import java.util.Objects;

import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class BytesRefValue {
	
	private BytesRef value;
	
	private DocValuesType docValuesType;

	public BytesRef getValue() {
		return value;
	}

	public void setValue(BytesRef value) {
		this.value = value;
	}

	public DocValuesType getDocValuesType() {
		return docValuesType;
	}

	public void setDocValuesType(DocValuesType docValuesType) {
		this.docValuesType = docValuesType;
	}

	public BytesRefValue(BytesRef value, DocValuesType docValuesType) {
		this.value = value;
		this.docValuesType = docValuesType;
	}
	
	
	

	@Override
	public String toString() {
		if(docValuesType==DocValuesType.NUMERIC) {
			return Long.toString(NumericUtils.sortableBytesToLong(value.bytes, 0));
		}else {
			return value.utf8ToString();
	
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(docValuesType, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BytesRefValue other = (BytesRefValue) obj;
		return docValuesType == other.docValuesType && Objects.equals(value, other.value);
	}
}
