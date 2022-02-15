package com.kanke.search.query.collector;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefArray;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.Counter;

public class TermValue {

	private int index = 0;

	private BytesRefBuilder bytesRefBuilder;

	private BytesRefArray bytesRefArray = new BytesRefArray(Counter.newCounter());

	private int[] groupIds;

	public TermValue(int num) {
		this.groupIds = new int[num];
		bytesRefBuilder = new BytesRefBuilder();
	}

	public void addValue(int groupId, BytesRef bytesRef) {
		this.groupIds[this.index] = groupId;
		this.index++;
		bytesRefBuilder.append(bytesRef);
		bytesRefArray.append(bytesRef);
	}

	public BytesRef toBytesRef() {
		return bytesRefBuilder.toBytesRef();
	}

	public BytesRef get(int index) {
		BytesRefBuilder spare = new BytesRefBuilder();
		return bytesRefArray.get(spare, index);

	}

}
