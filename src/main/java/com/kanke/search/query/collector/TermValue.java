package com.kanke.search.query.collector;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefArray;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.NumericUtils;

public class TermValue {

	private BytesRefBuilder bytesRefBuilder;

	private BytesRefArray bytesRefArray = new BytesRefArray(Counter.newCounter());
	

	public TermValue() {
		bytesRefBuilder = new BytesRefBuilder();
	}

	public void addValue(BytesRef bytesRef) {
		bytesRefArray.append(bytesRef);
		bytesRefBuilder.append(bytesRef);
	}

	public void addValue(byte[] bytes) {
		BytesRef bytesRef = new BytesRef(bytes);
		this.addValue(bytesRef);
	}

	
	
	public void addValue(long num) {
		byte[] data = new byte[8];
		NumericUtils.longToSortableBytes(num, data, 0);
		this.addValue(data);
	}

	public BytesRef toBytesRef() {
		return bytesRefBuilder.toBytesRef();
	}

	public int getSize() {
		return bytesRefArray.size();
	}

	public BytesRef get(int index) {
		BytesRefBuilder spare = new BytesRefBuilder();
		return bytesRefArray.get(spare, index);

	}

	public String toString(int num) {
		return this.get(num).utf8ToString();
	}
	@Override
	public String toString() {
		return this.get(0).utf8ToString();
	}


}
